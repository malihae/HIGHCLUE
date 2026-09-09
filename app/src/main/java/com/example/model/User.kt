package com.example.model

data class UserProfile(
    val userId: String = "guest_student",
    val displayName: String = "Curious Explorer",
    val email: String = "",
    val ageGroup: String = "14–17", // "11–13", "14–17"
    val preferredStream: String = "Undecided / Exploring",
    val isAnonymous: Boolean = false,
    val geniallyConnected: Boolean = false,
    val geniallyAccountEmail: String? = null
)

data class ModuleProgress(
    val courseId: String,
    val moduleId: String,
    val isCompleted: Boolean = false,
    val quizScore: Int? = null,
    val completedAtTimestamp: Long = 0L
)

data class CourseProgress(
    val courseId: String,
    val completedModulesCount: Int = 0,
    val totalModulesCount: Int = 0,
    val percentage: Int = 0,
    val lastAccessedTimestamp: Long = System.currentTimeMillis(),
    val isBookmarked: Boolean = false
)

data class StudentNote(
    val id: String,
    val userId: String,
    val courseId: String? = null,
    val courseTitle: String? = null,
    val moduleId: String? = null,
    val moduleTitle: String? = null,
    val title: String,
    val content: String,
    val tags: List<String> = emptyList(),
    val isBookmarked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class AssessmentDimensionScore(
    val dimensionId: String,
    val dimensionName: String,
    val score: Float, // 0.0 to 1.0
    val strengthLabel: String
)

data class AssessmentResult(
    val id: String,
    val userId: String,
    val completedAt: Long = System.currentTimeMillis(),
    val dimensionScores: Map<String, Float>,
    val topInterestAreas: List<String>,
    val suggestedCourseIds: List<String>,
    val suggestedSkills: List<String>,
    val relatedCareerDomains: List<String>,
    val recommendedNextSteps: List<String>,
    val explorationNotice: String = "These results are exploratory recommendations to spark your curiosity, not a definitive career prediction or rigid test."
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean,
    val unlockedAt: Long? = null
)
