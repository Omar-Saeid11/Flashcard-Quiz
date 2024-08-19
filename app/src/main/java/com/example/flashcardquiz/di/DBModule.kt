package com.example.flashcardquiz.di

import android.content.Context
import com.example.flashcardquiz.db.FlashcardDao
import com.example.flashcardquiz.db.FlashcardDatabase
import com.example.flashcardquiz.db.QuizResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FlashcardDatabase {
        return FlashcardDatabase.getDatabase(context)
    }

    @Provides
    fun provideFlashcardDao(database: FlashcardDatabase): FlashcardDao {
        return database.flashcardDao()
    }

    @Provides
    fun provideQuizResultDao(database: FlashcardDatabase): QuizResultDao {
        return database.quizResultDao()
    }
}
