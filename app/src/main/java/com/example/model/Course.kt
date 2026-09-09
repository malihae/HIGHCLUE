package com.example.model

data class MiniQuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class VisualActivity(
    val title: String,
    val description: String,
    val interactiveType: String, // "STEPPER", "CHOICE_SIMULATION", "LOGIC_PUZZLE", "EXPLORER"
    val stepsOrScenarios: List<String>,
    val takeaway: String
)

data class CourseModule(
    val id: String,
    val courseId: String,
    val title: String,
    val orderIndex: Int,
    val learningObjective: String,
    val explanation: String,
    val visualActivity: VisualActivity,
    val realWorldExample: String,
    val careerConnection: String,
    val skillDeveloped: String,
    val estimatedMinutes: Int,
    val quiz: MiniQuizQuestion,
    val geniallyUrl: String? = null,
    val geniallyTitle: String? = null
)

data class Course(
    val id: String,
    val title: String,
    val domain: String, // Technology, Science, Health, Engineering, Business, Creative, Social Sciences, Environment
    val category: String,
    val shortDescription: String,
    val fullDescription: String,
    val difficulty: String, // Beginner-friendly, Intermediate
    val estimatedTime: String,
    val skills: List<String>,
    val iconName: String,
    val colorHex: String,
    val modules: List<CourseModule>,
    val geniallyTemplateUrl: String? = null,
    val universityMajors: List<String> = emptyList(),
    val highSchoolSubjects: List<String> = emptyList()
)
