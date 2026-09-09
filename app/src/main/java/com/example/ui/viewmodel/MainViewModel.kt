package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CourseRepository
import com.example.data.GeniallyIntegrationManager
import com.example.data.MlRecommendationEngine
import com.example.data.RagKnowledgeBase
import com.example.data.local.AssessmentEntity
import com.example.data.local.CourseBookmarkEntity
import com.example.data.local.HighClueDatabase
import com.example.data.local.ModuleProgressEntity
import com.example.data.local.NoteEntity
import com.example.model.Achievement
import com.example.model.AssessmentResult
import com.example.model.Course
import com.example.model.CourseModule
import com.example.model.CourseProgress
import com.example.model.MlRecommendationItem
import com.example.model.RagMessage
import com.example.model.StudentNote
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = HighClueDatabase.getInstance(application)
    private val noteDao = db.noteDao()
    private val progressDao = db.progressDao()
    private val assessmentDao = db.assessmentDao()

    // Current User Profile
    private val _userProfile = MutableStateFlow(
        UserProfile(
            userId = "student_explorer_1",
            displayName = "Alex Carter",
            email = "alex@highclue.edu",
            ageGroup = "14–17",
            preferredStream = "Exploring Tech & Sciences"
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // Search and Filtering
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedDomain = MutableStateFlow("All")
    val selectedDomain: StateFlow<String> = _selectedDomain.asStateFlow()

    // Filtered Courses
    val filteredCourses: StateFlow<List<Course>> = combine(
        _searchQuery,
        _selectedDomain
    ) { query, domain ->
        CourseRepository.searchCourses(query, domain)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CourseRepository.courses)

    // Current Active Course & Module
    private val _selectedCourseId = MutableStateFlow<String?>("cs_software")
    val selectedCourseId: StateFlow<String?> = _selectedCourseId.asStateFlow()

    val currentCourse: StateFlow<Course?> = _selectedCourseId.combine(filteredCourses) { id, _ ->
        id?.let { CourseRepository.getCourseById(it) } ?: CourseRepository.courses.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CourseRepository.courses.firstOrNull())

    private val _selectedModuleId = MutableStateFlow<String?>("cs_m1")
    val selectedModuleId: StateFlow<String?> = _selectedModuleId.asStateFlow()

    val currentModule: StateFlow<CourseModule?> = combine(
        currentCourse,
        _selectedModuleId
    ) { course, modId ->
        course?.modules?.find { it.id == modId } ?: course?.modules?.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Local Progress and Notes via Room
    val notesFlow = noteDao.getNotesForUser(_userProfile.value.userId)
        .combine(_userProfile) { list, _ ->
            list.map { it.toDomain() }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val moduleProgressList = progressDao.getAllModuleProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val courseBookmarks = progressDao.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Aggregated Course Progress Map
    val progressMap: StateFlow<Map<String, CourseProgress>> = combine(
        moduleProgressList,
        courseBookmarks
    ) { modules, bookmarks ->
        val bookmarkMap = bookmarks.associateBy { it.courseId }
        CourseRepository.courses.associate { course ->
            val courseCompleted = modules.filter { it.courseId == course.id && it.isCompleted }
            val total = course.modules.size
            val percentage = if (total > 0) (courseCompleted.size * 100) / total else 0
            val isBm = bookmarkMap[course.id]?.isBookmarked ?: false
            course.id to CourseProgress(
                courseId = course.id,
                completedModulesCount = courseCompleted.size,
                totalModulesCount = total,
                percentage = percentage,
                isBookmarked = isBm
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // Overall Progress Percentage
    val overallProgressPercent: StateFlow<Int> = progressMap.combine(filteredCourses) { map, _ ->
        val totalCourses = CourseRepository.courses.size
        if (totalCourses == 0) 0 else map.values.sumOf { it.percentage } / totalCourses
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Achievements List
    val achievements: StateFlow<List<Achievement>> = combine(
        moduleProgressList,
        notesFlow
    ) { modules, notes ->
        val completedCount = modules.count { it.isCompleted }
        val quizAces = modules.count { (it.quizScore ?: 0) >= 100 }
        val uniqueDomainsExplored = modules.filter { it.isCompleted }.mapNotNull {
            CourseRepository.getCourseById(it.courseId)?.domain
        }.distinct().size

        listOf(
            Achievement(
                id = "ach_first_module",
                title = "Curiosity Spark",
                description = "Complete your first interactive career module.",
                iconName = "Bolt",
                isUnlocked = completedCount >= 1
            ),
            Achievement(
                id = "ach_quiz_ace",
                title = "Quiz Master",
                description = "Answer a module mini quiz correctly on your first attempt.",
                iconName = "EmojiEvents",
                isUnlocked = quizAces >= 1
            ),
            Achievement(
                id = "ach_multi_domain",
                title = "Broad Horizon",
                description = "Explore modules in at least 3 distinct career domains.",
                iconName = "Explore",
                isUnlocked = uniqueDomainsExplored >= 3
            ),
            Achievement(
                id = "ach_note_taker",
                title = "Diligent Thinker",
                description = "Save 2 or more student exploration notes.",
                iconName = "MenuBook",
                isUnlocked = notes.size >= 2
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ML Recommendations
    val mlRecommendations: StateFlow<List<MlRecommendationItem>> = combine(
        progressMap,
        notesFlow
    ) { pMap, notes ->
        MlRecommendationEngine.rankCourses(
            courses = CourseRepository.courses,
            assessmentScores = latestAssessment.value?.dimensionScores,
            progressMap = pMap,
            studentNotes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Assessment State (14 Dimensions)
    val assessmentQuestions = listOf(
        AssessmentQuestion("tech", "Technology & Coding", "I enjoy understanding how computers, software, and gadgets work under the hood."),
        AssessmentQuestion("science", "Scientific Discovery", "I love conducting experiments, observing biology, and understanding how the physical universe works."),
        AssessmentQuestion("math", "Mathematics & Logic", "Working with numbers, statistical graphs, and logic puzzles feels rewarding to me."),
        AssessmentQuestion("creativity", "Creativity & Art", "I like expressing visual ideas, sketching designs, or making films and media."),
        AssessmentQuestion("helping", "Helping People & Health", "I find deep satisfaction in helping others recover from illness or advising people through hardships."),
        AssessmentQuestion("business", "Business & Entrepreneurship", "I enjoy organizing projects, selling products, or imagining new startup companies."),
        AssessmentQuestion("analytical", "Analytical Thinking", "I prefer analyzing facts, comparing options, and finding patterns before making decisions."),
        AssessmentQuestion("hands_on", "Hands-On Building", "I love physically assembling things, wiring electronics, or building models."),
        AssessmentQuestion("communication", "Communication & Debate", "I enjoy presenting ideas, public speaking, writing persuasive essays, or debating points of view."),
        AssessmentQuestion("problem_solving", "Problem Solving", "When faced with a complex puzzle, I enjoy breaking it into smaller manageable steps."),
        AssessmentQuestion("law", "Justice & Ethics", "I care strongly about rules of fairness, human rights, and social policy."),
        AssessmentQuestion("environment", "Environment & Nature", "I feel passionate about protecting climate systems, wildlife, and natural ecosystems."),
        AssessmentQuestion("psychology", "Human Behavior", "I wonder about why people behave the way they do and how the human brain functions."),
        AssessmentQuestion("media", "Media & Storytelling", "I like telling compelling stories through writing, podcasts, videos, or animations.")
    )

    private val _assessmentAnswers = MutableStateFlow<Map<String, Float>>(
        assessmentQuestions.associate { it.dimensionId to 0.5f }
    )
    val assessmentAnswers: StateFlow<Map<String, Float>> = _assessmentAnswers.asStateFlow()

    private val _latestAssessment = MutableStateFlow<AssessmentResult?>(null)
    val latestAssessment: StateFlow<AssessmentResult?> = _latestAssessment.asStateFlow()

    // RAG Assistant State
    private val _ragMessages = MutableStateFlow<List<RagMessage>>(
        listOf(
            RagMessage(
                id = "welcome_rag",
                sender = "assistant",
                text = "Hi! I'm HighClue's AI Career Advisor. Ask me anything about high school stream choices, university majors, day-to-day job roles, or which subjects you should study for careers like Software, Medicine, Robotics, or Law!"
            )
        )
    )
    val ragMessages: StateFlow<List<RagMessage>> = _ragMessages.asStateFlow()

    private val _isRagThinking = MutableStateFlow(false)
    val isRagThinking: StateFlow<Boolean> = _isRagThinking.asStateFlow()

    // Genially Interactive Viewer State
    private val _activeGeniallyUrl = MutableStateFlow<String?>(null)
    val activeGeniallyUrl: StateFlow<String?> = _activeGeniallyUrl.asStateFlow()

    // Actions
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedDomain(domain: String) {
        _selectedDomain.value = domain
    }

    fun selectCourse(courseId: String) {
        _selectedCourseId.value = courseId
        val course = CourseRepository.getCourseById(courseId)
        _selectedModuleId.value = course?.modules?.firstOrNull()?.id
    }

    fun selectModule(moduleId: String) {
        _selectedModuleId.value = moduleId
    }

    fun toggleModuleCompletion(courseId: String, moduleId: String, quizScore: Int? = null) {
        viewModelScope.launch {
            val existing = moduleProgressList.value.find { it.courseId == courseId && it.moduleId == moduleId }
            val newCompleted = !(existing?.isCompleted ?: false)
            progressDao.saveModuleProgress(
                ModuleProgressEntity(
                    courseId = courseId,
                    moduleId = moduleId,
                    isCompleted = newCompleted,
                    quizScore = quizScore ?: existing?.quizScore ?: 100,
                    completedAtTimestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun toggleCourseBookmark(courseId: String) {
        viewModelScope.launch {
            val existing = courseBookmarks.value.find { it.courseId == courseId }
            val newStatus = !(existing?.isBookmarked ?: false)
            progressDao.saveBookmark(
                CourseBookmarkEntity(
                    courseId = courseId,
                    isBookmarked = newStatus,
                    lastAccessedTimestamp = System.currentTimeMillis()
                )
            )
        }
    }

    // Notes Actions
    fun saveNote(
        id: String = UUID.randomUUID().toString(),
        title: String,
        content: String,
        tags: List<String>,
        courseId: String? = currentCourse.value?.id,
        moduleId: String? = currentModule.value?.id
    ) {
        viewModelScope.launch {
            val entity = NoteEntity(
                id = id,
                userId = _userProfile.value.userId,
                courseId = courseId,
                courseTitle = CourseRepository.getCourseById(courseId ?: "")?.title,
                moduleId = moduleId,
                moduleTitle = currentModule.value?.title,
                title = title.ifBlank { "Untitled Note" },
                content = content,
                tagsCsv = tags.joinToString(","),
                isBookmarked = false,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            noteDao.insertNote(entity)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            noteDao.deleteNoteById(noteId)
        }
    }

    // Assessment Actions
    fun updateAssessmentDimension(dimensionId: String, score: Float) {
        _assessmentAnswers.value = _assessmentAnswers.value.toMutableMap().apply {
            put(dimensionId, score)
        }
    }

    fun submitAssessment() {
        val scores = _assessmentAnswers.value
        val sortedDimensions = scores.entries.sortedByDescending { it.value }
        val top3 = sortedDimensions.take(3).map { it.key }

        val dimensionNameMap = mapOf(
            "tech" to "Technology & Software",
            "science" to "Natural Sciences & Biology",
            "math" to "Mathematics & Data",
            "creativity" to "Design & Visual Arts",
            "helping" to "Healthcare & Human Services",
            "business" to "Business & Leadership",
            "analytical" to "Analytical Systems",
            "hands_on" to "Engineering & Robotics",
            "communication" to "Law, Debate & Policy",
            "problem_solving" to "Complex Problem Solving",
            "law" to "Justice & Social Sciences",
            "environment" to "Environmental Science & Climate",
            "psychology" to "Psychology & Behavioral Science",
            "media" to "Media & Storytelling"
        )

        val topInterestLabels = top3.map { dimensionNameMap[it] ?: it.replaceFirstChar { c -> c.uppercase() } }

        val suggestedCourses = CourseRepository.courses.filter { course ->
            top3.any { dim ->
                when (dim) {
                    "tech" -> course.domain == "Technology"
                    "science" -> course.domain == "Science" || course.domain == "Health"
                    "hands_on" -> course.domain == "Engineering"
                    "helping" -> course.domain == "Health"
                    "business" -> course.domain == "Business"
                    "law" -> course.domain == "Social Sciences"
                    "creativity", "media" -> course.domain == "Creative"
                    "environment" -> course.domain == "Environment"
                    else -> false
                }
            }
        }.take(4).map { it.id }

        val result = AssessmentResult(
            id = UUID.randomUUID().toString(),
            userId = _userProfile.value.userId,
            dimensionScores = scores,
            topInterestAreas = topInterestLabels,
            suggestedCourseIds = suggestedCourses.ifEmpty { listOf("cs_software", "robotics", "medicine_health") },
            suggestedSkills = listOf("Critical Investigation", "Scientific Method", "Design Architecture", "Empathy & Communication"),
            relatedCareerDomains = topInterestLabels,
            recommendedNextSteps = listOf(
                "Take the first 2 modules in ${CourseRepository.getCourseById(suggestedCourses.firstOrNull() ?: "cs_software")?.title ?: "Computer Science"}.",
                "Save your personal takeaways in Student Notes after each interactive activity.",
                "Consult the RAG Career Guide for recommended high school elective streams."
            )
        )

        _latestAssessment.value = result

        viewModelScope.launch {
            assessmentDao.saveAssessment(
                AssessmentEntity(
                    id = result.id,
                    userId = result.userId,
                    completedAt = result.completedAt,
                    scoresJson = scores.entries.joinToString(";") { "${it.key}:${it.value}" },
                    topInterestsCsv = result.topInterestAreas.joinToString(","),
                    suggestedCoursesCsv = result.suggestedCourseIds.joinToString(",")
                )
            )
        }
    }

    // RAG Guidance Actions
    fun askRagAssistant(question: String) {
        val trimmed = question.trim()
        if (trimmed.isBlank() || _isRagThinking.value) return

        val userMsg = RagMessage(
            id = UUID.randomUUID().toString(),
            sender = "user",
            text = trimmed
        )
        _ragMessages.value = _ragMessages.value + userMsg
        _isRagThinking.value = true

        viewModelScope.launch {
            try {
                val response = RagKnowledgeBase.askCareerAssistant(trimmed)
                val assistantMsg = RagMessage(
                    id = UUID.randomUUID().toString(),
                    sender = "assistant",
                    text = response.answer,
                    citedSources = response.citedSources
                )
                _ragMessages.value = _ragMessages.value + assistantMsg
            } catch (e: Exception) {
                val fallbackMsg = RagMessage(
                    id = UUID.randomUUID().toString(),
                    sender = "assistant",
                    text = "I ran into a connection glitch, but HighClue's knowledge base indicates that strong high school math, sciences, and communication are universally valued across college majors."
                )
                _ragMessages.value = _ragMessages.value + fallbackMsg
            } finally {
                _isRagThinking.value = false
            }
        }
    }

    // Genially Launcher
    fun openGeniallyLesson(url: String?) {
        _activeGeniallyUrl.value = url ?: currentCourse.value?.geniallyTemplateUrl
    }

    fun closeGeniallyLesson() {
        _activeGeniallyUrl.value = null
    }

    // Auth & User Profile Updates
    fun updateProfile(name: String, ageGroup: String, preferredStream: String) {
        _userProfile.value = _userProfile.value.copy(
            displayName = name.ifBlank { "Curious Explorer" },
            ageGroup = ageGroup,
            preferredStream = preferredStream
        )
    }

    fun connectGeniallyAccount(email: String, token: String): Result<String> {
        val res = GeniallyIntegrationManager.connectAccount(email, token)
        return if (res.isSuccess) {
            _userProfile.value = _userProfile.value.copy(
                geniallyConnected = true,
                geniallyAccountEmail = email
            )
            Result.success("Connected to Genially for $email")
        } else {
            Result.failure(res.exceptionOrNull() ?: Exception("Failed to connect"))
        }
    }

    fun logout() {
        _userProfile.value = UserProfile(
            userId = "guest_student",
            displayName = "Guest Explorer",
            email = "",
            isAnonymous = true
        )
    }
}

data class AssessmentQuestion(
    val dimensionId: String,
    val dimensionName: String,
    val statement: String
)

private fun NoteEntity.toDomain(): StudentNote {
    return StudentNote(
        id = id,
        userId = userId,
        courseId = courseId,
        courseTitle = courseTitle,
        moduleId = moduleId,
        moduleTitle = moduleTitle,
        title = title,
        content = content,
        tags = if (tagsCsv.isBlank()) emptyList() else tagsCsv.split(","),
        isBookmarked = isBookmarked,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
