package com.example.flashcardquiz.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardquiz.data.Flashcard
import com.example.flashcardquiz.data.QuizResult
import com.example.flashcardquiz.data.db.FlashcardDao
import com.example.flashcardquiz.data.db.QuizResultDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FlashcardViewModel @Inject constructor(
    private val flashcardDao: FlashcardDao,
    private val quizResultDao: QuizResultDao
) : ViewModel() {

    private val _flashcards = mutableStateListOf<Flashcard>()
    val flashcards: List<Flashcard> = _flashcards

    private val _quizHistory = mutableStateListOf<QuizResult>()
    val quizHistory: List<QuizResult> = _quizHistory

    var score by mutableIntStateOf(0)
        private set

    init {
        loadFlashcards()
        loadQuizHistory()
    }

    private fun loadFlashcards() {
        viewModelScope.launch {
            val flashcardsFromDb = withContext(Dispatchers.IO) {
                flashcardDao.getAllFlashcards()
            }
            _flashcards.addAll(flashcardsFromDb)
        }
    }

    private fun loadQuizHistory() {
        viewModelScope.launch {
            val quizHistoryFromDb = withContext(Dispatchers.IO) {
                quizResultDao.getAllQuizResults()
            }
            _quizHistory.addAll(quizHistoryFromDb)
        }
    }

    fun addFlashcard(question: String, answer: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val flashcard = Flashcard(question = question, answer = answer)
            flashcardDao.insertFlashcard(flashcard)
            withContext(Dispatchers.Main) {
                _flashcards.add(flashcard)
            }
        }
    }

    fun checkAnswer(index: Int, userAnswer: String) {
        if (_flashcards[index].answer.equals(userAnswer, ignoreCase = true)) {
            score++
        }
    }

    fun saveQuizResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val date =
                java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
                    .format(java.util.Date())
            val currentScore = score
            val quizResult = QuizResult(score = currentScore, date = date)
            quizResultDao.insertQuizResult(quizResult)
            withContext(Dispatchers.Main) {
                _quizHistory.add(quizResult)
                resetQuiz()
            }
        }
    }


    private fun resetQuiz() {
        score = 0
    }
}
