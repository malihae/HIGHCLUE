package com.example.data.firebase

import com.example.model.CourseProgress
import com.example.model.StudentNote
import com.example.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseManager {

    val firestoreRulesDefinition: String = """
        rules_version = '2';
        service cloud.firestore {
          match /databases/{database}/documents {
            // Helper function to verify authenticated owner
            function isOwner(userId) {
              return request.auth != null && request.auth.uid == userId;
            }
            
            // Public read-only educational courses and modules
            match /courses/{courseId} {
              allow read: if true;
              allow write: if false; // Only admin service accounts can modify curriculum
              
              match /modules/{moduleId} {
                allow read: if true;
                allow write: if false;
              }
            }
            
            // Private user profiles - Minor privacy protection (COPPA compliant)
            match /users/{userId} {
              allow read, write: if isOwner(userId);
            }
            
            match /profiles/{userId} {
              allow read, write: if isOwner(userId);
            }
            
            // Private student progress
            match /progress/{userId}/courses/{courseId} {
              allow read, write: if isOwner(userId);
            }
            
            // Private student interest assessments
            match /assessments/{userId} {
              allow read, write: if isOwner(userId);
            }
            
            // Private student notes
            match /notes/{userId}/notes/{noteId} {
              allow read, write: if isOwner(userId);
            }
            
            // Private quiz results
            match /quizResults/{userId}/results/{resultId} {
              allow read, write: if isOwner(userId);
            }
            
            // Private recommendations
            match /recommendations/{userId} {
              allow read, write: if isOwner(userId);
            }
          }
        }
    """.trimIndent()

    fun isFirebaseConfigured(): Boolean {
        return try {
            FirebaseAuth.getInstance().app != null
        } catch (e: Exception) {
            false
        }
    }

    suspend fun syncNoteToFirestore(note: StudentNote): Boolean {
        return try {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser ?: return false
            val db = FirebaseFirestore.getInstance()
            
            val noteMap = hashMapOf(
                "id" to note.id,
                "courseId" to note.courseId,
                "courseTitle" to note.courseTitle,
                "moduleId" to note.moduleId,
                "moduleTitle" to note.moduleTitle,
                "title" to note.title,
                "content" to note.content,
                "tags" to note.tags,
                "isBookmarked" to note.isBookmarked,
                "updatedAt" to note.updatedAt
            )
            
            db.collection("notes")
                .document(user.uid)
                .collection("notes")
                .document(note.id)
                .set(noteMap)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun syncProgressToFirestore(progress: CourseProgress): Boolean {
        return try {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser ?: return false
            val db = FirebaseFirestore.getInstance()

            val progressMap = hashMapOf(
                "courseId" to progress.courseId,
                "completedModulesCount" to progress.completedModulesCount,
                "totalModulesCount" to progress.totalModulesCount,
                "percentage" to progress.percentage,
                "lastAccessedTimestamp" to progress.lastAccessedTimestamp,
                "isBookmarked" to progress.isBookmarked
            )

            db.collection("progress")
                .document(user.uid)
                .collection("courses")
                .document(progress.courseId)
                .set(progressMap)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
