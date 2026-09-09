package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM student_notes WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getNotesForUser(userId: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM student_notes WHERE userId = :userId AND courseId = :courseId ORDER BY updatedAt DESC")
    fun getNotesForCourse(userId: String, courseId: String): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM student_notes WHERE id = :id")
    suspend fun deleteNoteById(id: String)
}

@Dao
interface ProgressDao {
    @Query("SELECT * FROM module_progress")
    fun getAllModuleProgress(): Flow<List<ModuleProgressEntity>>

    @Query("SELECT * FROM module_progress WHERE courseId = :courseId")
    fun getProgressForCourse(courseId: String): Flow<List<ModuleProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveModuleProgress(progress: ModuleProgressEntity)

    @Query("SELECT * FROM course_bookmarks")
    fun getAllBookmarks(): Flow<List<CourseBookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookmark(bookmark: CourseBookmarkEntity)
}

@Dao
interface AssessmentDao {
    @Query("SELECT * FROM assessment_history WHERE userId = :userId ORDER BY completedAt DESC LIMIT 1")
    fun getLatestAssessment(userId: String): Flow<AssessmentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAssessment(assessment: AssessmentEntity)
}
