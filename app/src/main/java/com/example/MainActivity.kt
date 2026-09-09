package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CourseRepository
import com.example.ui.components.GeniallyViewerDialog
import com.example.ui.components.HighClueBottomNav
import com.example.ui.components.HighClueTopBar
import com.example.ui.components.Screen
import com.example.ui.screens.AiGuidanceScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CompletionScreen
import com.example.ui.screens.CourseDetailScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InterestLevelerScreen
import com.example.ui.screens.ModuleViewerScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.ProfileSettingsScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HighClueApp()
            }
        }
    }
}

@Composable
fun HighClueApp(viewModel: MainViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }

    // State collection
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedDomain by viewModel.selectedDomain.collectAsStateWithLifecycle()
    val filteredCourses by viewModel.filteredCourses.collectAsStateWithLifecycle()
    val currentCourse by viewModel.currentCourse.collectAsStateWithLifecycle()
    val currentModule by viewModel.currentModule.collectAsStateWithLifecycle()
    val notes by viewModel.notesFlow.collectAsStateWithLifecycle()
    val moduleProgressList by viewModel.moduleProgressList.collectAsStateWithLifecycle()
    val progressMap by viewModel.progressMap.collectAsStateWithLifecycle()
    val overallProgress by viewModel.overallProgressPercent.collectAsStateWithLifecycle()
    val achievements by viewModel.achievements.collectAsStateWithLifecycle()
    val mlRecommendations by viewModel.mlRecommendations.collectAsStateWithLifecycle()
    val assessmentAnswers by viewModel.assessmentAnswers.collectAsStateWithLifecycle()
    val latestAssessment by viewModel.latestAssessment.collectAsStateWithLifecycle()
    val ragMessages by viewModel.ragMessages.collectAsStateWithLifecycle()
    val isRagThinking by viewModel.isRagThinking.collectAsStateWithLifecycle()
    val activeGeniallyUrl by viewModel.activeGeniallyUrl.collectAsStateWithLifecycle()

    // Smart Back Handler
    BackHandler(enabled = currentScreen != Screen.Home && currentScreen != Screen.Splash) {
        currentScreen = when (currentScreen) {
            Screen.ModuleViewer -> Screen.CourseDetail
            Screen.Completion -> Screen.CourseDetail
            Screen.CourseDetail -> Screen.Explore
            Screen.Auth -> Screen.Splash
            else -> Screen.Home
        }
    }

    val showBars = currentScreen != Screen.Splash && currentScreen != Screen.Auth

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showBars) {
                HighClueTopBar(
                    currentScreen = currentScreen,
                    onNavigate = { currentScreen = it },
                    onSearchClick = { currentScreen = Screen.Explore }
                )
            }
        },
        bottomBar = {
            if (showBars) {
                HighClueBottomNav(
                    currentScreen = currentScreen,
                    onNavigate = { currentScreen = it }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.Splash -> {
                    SplashScreen(
                        onStartExploring = { currentScreen = Screen.Home },
                        onSignIn = { currentScreen = Screen.Auth }
                    )
                }

                Screen.Auth -> {
                    AuthScreen(
                        onAuthSuccess = { displayName ->
                            viewModel.updateProfile(displayName, "14–17", "Exploring Tech & Sciences")
                            currentScreen = Screen.Home
                        },
                        onGuestContinue = { currentScreen = Screen.Home }
                    )
                }

                Screen.Home -> {
                    HomeScreen(
                        userProfile = userProfile,
                        allCourses = CourseRepository.courses,
                        progressMap = progressMap,
                        mlRecommendations = mlRecommendations,
                        onCourseClick = { courseId ->
                            viewModel.selectCourse(courseId)
                            currentScreen = Screen.CourseDetail
                        },
                        onNavigate = { screen -> currentScreen = screen },
                        onAskAi = { sparkQuestion ->
                            viewModel.askRagAssistant(sparkQuestion)
                            currentScreen = Screen.AiGuidance
                        }
                    )
                }

                Screen.Dashboard -> {
                    DashboardScreen(
                        userProfile = userProfile,
                        allCourses = CourseRepository.courses,
                        progressMap = progressMap,
                        overallProgress = overallProgress,
                        notes = notes,
                        achievements = achievements,
                        latestAssessment = latestAssessment,
                        mlRecommendations = mlRecommendations,
                        onCourseClick = { courseId ->
                            viewModel.selectCourse(courseId)
                            currentScreen = Screen.CourseDetail
                        },
                        onNavigate = { screen -> currentScreen = screen }
                    )
                }

                Screen.Explore -> {
                    ExploreScreen(
                        courses = filteredCourses,
                        progressMap = progressMap,
                        searchQuery = searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        selectedDomain = selectedDomain,
                        onSelectDomain = { viewModel.setSelectedDomain(it) },
                        onCourseClick = { courseId ->
                            viewModel.selectCourse(courseId)
                            currentScreen = Screen.CourseDetail
                        },
                        onBookmarkToggle = { courseId ->
                            viewModel.toggleCourseBookmark(courseId)
                        }
                    )
                }

                Screen.CourseDetail -> {
                    val course = currentCourse ?: CourseRepository.courses.first()
                    CourseDetailScreen(
                        course = course,
                        progress = progressMap[course.id],
                        moduleProgressList = moduleProgressList,
                        onBack = { currentScreen = Screen.Explore },
                        onModuleSelect = { modId ->
                            viewModel.selectModule(modId)
                            currentScreen = Screen.ModuleViewer
                        },
                        onLaunchGenially = { url ->
                            viewModel.openGeniallyLesson(url)
                        },
                        onBookmarkToggle = {
                            viewModel.toggleCourseBookmark(course.id)
                        },
                        onViewCompletion = {
                            currentScreen = Screen.Completion
                        }
                    )
                }

                Screen.ModuleViewer -> {
                    val course = currentCourse ?: CourseRepository.courses.first()
                    val module = currentModule ?: course.modules.first()
                    val isCompleted = moduleProgressList.any {
                        it.courseId == course.id && it.moduleId == module.id && it.isCompleted
                    }
                    ModuleViewerScreen(
                        course = course,
                        module = module,
                        isCompleted = isCompleted,
                        onBack = { currentScreen = Screen.CourseDetail },
                        onToggleCompletion = { quizScore ->
                            viewModel.toggleModuleCompletion(course.id, module.id, quizScore)
                        },
                        onSaveNote = { title, content, tags ->
                            viewModel.saveNote(
                                title = title,
                                content = content,
                                tags = tags,
                                courseId = course.id,
                                moduleId = module.id
                            )
                        },
                        onOpenGenially = { url ->
                            viewModel.openGeniallyLesson(url)
                        }
                    )
                }

                Screen.Completion -> {
                    val course = currentCourse ?: CourseRepository.courses.first()
                    val related = CourseRepository.courses.filter { it.domain == course.domain && it.id != course.id }
                        .ifEmpty { CourseRepository.courses.filter { it.id != course.id }.take(2) }

                    CompletionScreen(
                        course = course,
                        progress = progressMap[course.id],
                        studentNotes = notes.filter { it.courseId == course.id },
                        relatedCourses = related,
                        onBackToCourse = { currentScreen = Screen.CourseDetail },
                        onNavigateHome = { currentScreen = Screen.Home },
                        onNavigateExplore = { currentScreen = Screen.Explore },
                        onNavigateAiGuidance = { question ->
                            viewModel.askRagAssistant(question)
                            currentScreen = Screen.AiGuidance
                        },
                        onCourseClick = { nextCourseId ->
                            viewModel.selectCourse(nextCourseId)
                            currentScreen = Screen.CourseDetail
                        }
                    )
                }

                Screen.InterestLeveler -> {
                    InterestLevelerScreen(
                        questions = viewModel.assessmentQuestions,
                        answers = assessmentAnswers,
                        latestResult = latestAssessment,
                        onAnswerChange = { dimId, score ->
                            viewModel.updateAssessmentDimension(dimId, score)
                        },
                        onSubmit = {
                            viewModel.submitAssessment()
                        },
                        onCourseClick = { courseId ->
                            viewModel.selectCourse(courseId)
                            currentScreen = Screen.CourseDetail
                        }
                    )
                }

                Screen.Progress -> {
                    ProgressScreen(
                        overallProgress = overallProgress,
                        progressMap = progressMap,
                        achievements = achievements
                    )
                }

                Screen.Notes -> {
                    NotesScreen(
                        notes = notes,
                        onSaveNote = { title, content, tags ->
                            viewModel.saveNote(
                                title = title,
                                content = content,
                                tags = tags,
                                courseId = currentCourse?.id,
                                moduleId = currentModule?.id
                            )
                        },
                        onDeleteNote = { noteId ->
                            viewModel.deleteNote(noteId)
                        }
                    )
                }

                Screen.AiGuidance -> {
                    AiGuidanceScreen(
                        messages = ragMessages,
                        isThinking = isRagThinking,
                        onSendMessage = { prompt ->
                            viewModel.askRagAssistant(prompt)
                        }
                    )
                }

                Screen.Profile -> {
                    ProfileSettingsScreen(
                        userProfile = userProfile,
                        onUpdateProfile = { name, ageGroup, stream ->
                            viewModel.updateProfile(name, ageGroup, stream)
                        },
                        onConnectGenially = { email, token ->
                            viewModel.connectGeniallyAccount(email, token)
                        },
                        onLogout = {
                            viewModel.logout()
                            currentScreen = Screen.Splash
                        }
                    )
                }
            }
        }
    }

    // Genially Interactive Modal Overlay
    activeGeniallyUrl?.let { url ->
        GeniallyViewerDialog(
            url = url,
            lessonTitle = currentCourse?.title ?: "Interactive Genially Lesson",
            onDismiss = { viewModel.closeGeniallyLesson() }
        )
    }
}
