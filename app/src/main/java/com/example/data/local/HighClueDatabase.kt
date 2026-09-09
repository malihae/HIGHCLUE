package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        NoteEntity::class,
        ModuleProgressEntity::class,
        CourseBookmarkEntity::class,
        AssessmentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HighClueDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun progressDao(): ProgressDao
    abstract fun assessmentDao(): AssessmentDao

    companion object {
        @Volatile
        private var INSTANCE: HighClueDatabase? = null

        fun getInstance(context: Context): HighClueDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HighClueDatabase::class.java,
                    "highclue_local.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
