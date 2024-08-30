package com.example.flashcardquiz.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flashcardquiz.data.QuizResult

@Dao
interface QuizResultDao {
    @Query("SELECT * FROM quiz_results")
    fun getAllQuizResults(): List<QuizResult>

    @Insert
    fun insertQuizResult(quizResult: QuizResult)
}