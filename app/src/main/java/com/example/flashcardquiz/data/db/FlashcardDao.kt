package com.example.flashcardquiz.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.flashcardquiz.data.Flashcard

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards")
    fun getAllFlashcards(): List<Flashcard>

    @Insert
    fun insertFlashcard(flashcard: Flashcard)

    @Delete
    fun deleteFlashcard(flashcard: Flashcard)
}