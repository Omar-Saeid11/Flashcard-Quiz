package com.example.flashcardquiz.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flashcardquiz.data.Flashcard
import com.example.flashcardquiz.data.QuizResult

@Database(entities = [Flashcard::class, QuizResult::class], version = 1)
abstract class FlashcardDatabase : RoomDatabase() {

    abstract fun flashcardDao(): FlashcardDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile
        private var INSTANCE: FlashcardDatabase? = null

        fun getDatabase(context: Context): FlashcardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashcardDatabase::class.java,
                    "flashcard_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
