package com.example.data

import com.example.model.Course
import com.example.model.CourseProgress
import com.example.model.MlDatasetDocumentation
import com.example.model.MlRecommendationItem
import com.example.model.StudentNote
import kotlin.math.sqrt

object MlRecommendationEngine {

    val datasetDocumentation = MlDatasetDocumentation()

    // Domain feature vectors across 14 assessment dimensions:
    // [Technology, Science, Math, Design/Creativity, Helping People, Business/Leadership, Analytical Thinking, Hands-On, Communication, Problem Solving, Law/Ethics, Environment, Psychology/Human Behavior, Media/Storytelling]
    private val domainVectorMap: Map<String, List<Float>> = mapOf(
        "cs_software" to listOf(0.95f, 0.4f, 0.85f, 0.3f, 0.2f, 0.4f, 0.9f, 0.5f, 0.4f, 0.95f, 0.2f, 0.1f, 0.2f, 0.3f),
        "ai_ml" to listOf(0.95f, 0.7f, 0.9f, 0.3f, 0.3f, 0.5f, 0.95f, 0.3f, 0.4f, 0.9f, 0.4f, 0.2f, 0.5f, 0.2f),
        "data_science" to listOf(0.85f, 0.6f, 0.9f, 0.4f, 0.3f, 0.7f, 0.95f, 0.2f, 0.6f, 0.85f, 0.3f, 0.3f, 0.4f, 0.4f),
        "cybersecurity" to listOf(0.9f, 0.3f, 0.8f, 0.2f, 0.5f, 0.4f, 0.9f, 0.4f, 0.5f, 0.95f, 0.8f, 0.1f, 0.4f, 0.2f),
        "robotics" to listOf(0.85f, 0.8f, 0.85f, 0.5f, 0.3f, 0.3f, 0.85f, 0.95f, 0.3f, 0.9f, 0.2f, 0.3f, 0.2f, 0.2f),
        "medicine_health" to listOf(0.4f, 0.95f, 0.5f, 0.2f, 0.95f, 0.3f, 0.85f, 0.6f, 0.8f, 0.9f, 0.5f, 0.3f, 0.7f, 0.2f),
        "engineering" to listOf(0.7f, 0.9f, 0.9f, 0.6f, 0.3f, 0.4f, 0.9f, 0.9f, 0.4f, 0.95f, 0.3f, 0.5f, 0.2f, 0.2f),
        "architecture_design" to listOf(0.5f, 0.5f, 0.7f, 0.95f, 0.4f, 0.5f, 0.7f, 0.8f, 0.6f, 0.8f, 0.3f, 0.7f, 0.5f, 0.7f),
        "business_ent" to listOf(0.4f, 0.3f, 0.6f, 0.6f, 0.5f, 0.95f, 0.8f, 0.3f, 0.9f, 0.85f, 0.6f, 0.4f, 0.7f, 0.6f),
        "finance_econ" to listOf(0.5f, 0.4f, 0.95f, 0.2f, 0.3f, 0.9f, 0.95f, 0.2f, 0.6f, 0.85f, 0.6f, 0.3f, 0.5f, 0.3f),
        "law_social" to listOf(0.2f, 0.3f, 0.4f, 0.3f, 0.8f, 0.7f, 0.85f, 0.2f, 0.95f, 0.85f, 0.98f, 0.4f, 0.8f, 0.6f),
        "psychology" to listOf(0.3f, 0.7f, 0.4f, 0.5f, 0.95f, 0.4f, 0.8f, 0.2f, 0.9f, 0.8f, 0.6f, 0.3f, 0.98f, 0.5f),
        "media_creation" to listOf(0.6f, 0.2f, 0.3f, 0.95f, 0.5f, 0.6f, 0.5f, 0.6f, 0.9f, 0.7f, 0.4f, 0.3f, 0.7f, 0.98f),
        "environmental_sci" to listOf(0.4f, 0.95f, 0.6f, 0.4f, 0.7f, 0.5f, 0.8f, 0.7f, 0.6f, 0.8f, 0.5f, 0.98f, 0.4f, 0.4f),
        "biotechnology" to listOf(0.6f, 0.98f, 0.7f, 0.3f, 0.8f, 0.4f, 0.9f, 0.7f, 0.5f, 0.9f, 0.6f, 0.6f, 0.4f, 0.2f)
    )

    /**
     * Compute multi-criteria ranking based on:
     * 1. Assessment vectors (if taken)
     * 2. Active course progress & quiz completion
     * 3. Student notes created
     * 4. Explicit domain interests
     */
    fun rankCourses(
        courses: List<Course>,
        assessmentScores: Map<String, Float>?,
        progressMap: Map<String, CourseProgress>,
        studentNotes: List<StudentNote>
    ): List<MlRecommendationItem> {
        val userVector = buildUserFeatureVector(assessmentScores, progressMap, studentNotes)

        val scoredItems = courses.map { course ->
            val courseVector = domainVectorMap[course.id] ?: List(14) { 0.5f }
            val baseSimilarity = cosineSimilarity(userVector, courseVector)

            // Dynamic interaction bonuses:
            val progress = progressMap[course.id]
            val progressBonus = when {
                progress != null && progress.percentage in 1..99 -> 0.12f // Encourage continuing started courses
                progress != null && progress.percentage == 100 -> 0.05f
                else -> 0.0f
            }

            // Note interest bonus:
            val notesForCourse = studentNotes.count { it.courseId == course.id }
            val notesBonus = (notesForCourse * 0.04f).coerceAtMost(0.12f)

            val totalScore = (baseSimilarity * 0.75f + progressBonus + notesBonus).coerceIn(0.1f, 0.99f)

            val reasoning = generateRecommendationReasoning(course, totalScore, assessmentScores, notesForCourse)
            val keySignals = generateKeySignals(course, progress, notesForCourse, totalScore)

            MlRecommendationItem(
                courseId = course.id,
                courseTitle = course.title,
                domain = course.domain,
                matchScore = totalScore,
                reasoning = reasoning,
                keySignals = keySignals
            )
        }

        return scoredItems.sortedByDescending { it.matchScore }
    }

    private fun buildUserFeatureVector(
        assessmentScores: Map<String, Float>?,
        progressMap: Map<String, CourseProgress>,
        studentNotes: List<StudentNote>
    ): List<Float> {
        val dimensions = listOf(
            "tech", "science", "math", "creativity", "helping",
            "business", "analytical", "hands_on", "communication",
            "problem_solving", "law", "environment", "psychology", "media"
        )

        return dimensions.map { dim ->
            val assessmentValue = assessmentScores?.get(dim) ?: 0.5f
            // Combine with behavioral weighting
            val notesTagged = studentNotes.count { note -> note.tags.any { it.contains(dim, ignoreCase = true) } }
            val behavioralAdjustment = (notesTagged * 0.05f).coerceAtMost(0.2f)
            (assessmentValue + behavioralAdjustment).coerceIn(0.1f, 1.0f)
        }
    }

    private fun cosineSimilarity(v1: List<Float>, v2: List<Float>): Float {
        var dot = 0.0f
        var normA = 0.0f
        var normB = 0.0f
        val size = minOf(v1.size, v2.size)

        for (i in 0 until size) {
            dot += v1[i] * v2[i]
            normA += v1[i] * v1[i]
            normB += v2[i] * v2[i]
        }

        if (normA == 0.0f || normB == 0.0f) return 0.5f
        return (dot / (sqrt(normA) * sqrt(normB))).coerceIn(0.0f, 1.0f)
    }

    private fun generateRecommendationReasoning(
        course: Course,
        score: Float,
        assessmentScores: Map<String, Float>?,
        notesCount: Int
    ): String {
        return when {
            score >= 0.85f -> "High alignment with your problem-solving style and exploratory interest in ${course.domain}."
            score >= 0.70f -> "Strong complement to your skill strengths in ${course.skills.take(2).joinToString(", ")}."
            notesCount > 0 -> "Matched based on your personal notes and active engagement with this domain."
            else -> "A great exploratory preview to expand your perspective in ${course.domain}."
        }
    }

    private fun generateKeySignals(
        course: Course,
        progress: CourseProgress?,
        notesCount: Int,
        score: Float
    ): List<String> {
        val signals = mutableListOf<String>()
        if (progress != null && progress.percentage > 0) {
            signals.add("${progress.percentage}% completed")
        }
        if (notesCount > 0) {
            signals.add("$notesCount saved note(s)")
        }
        signals.add("Fit: ${(score * 100).toInt()}%")
        signals.add(course.difficulty)
        return signals
    }
}
