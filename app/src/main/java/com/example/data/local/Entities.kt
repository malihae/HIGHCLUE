package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val courseId: String?,
    val courseTitle: String?,
    val moduleId: String?,
    val moduleTitle: String?,
    val title: String,
    val content: String,
    val tagsCsv: String,
    val isBookmarked: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "module_progress", primaryKeys = ["courseId", "moduleId"])
data class ModuleProgressEntity(
    val courseId: String,
    val moduleId: String,
    val isCompleted: Boolean,
    val quizScore: Int?,
    val completedAtTimestamp: Long
)

@Entity(tableName = "course_bookmarks")
data class CourseBookmarkEntity(
    @PrimaryKey val courseId: String,
    val isBookmarked: Boolean,
    val lastAccessedTimestamp: Long
)

@Entity(tableName = "assessment_history")
data class AssessmentEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val completedAt: Long,
    val scoresJson: String,
    val topInterestsCsv: String,
    val suggestedCoursesCsv: String
)
