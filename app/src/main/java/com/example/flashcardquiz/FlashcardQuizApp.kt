package com.example.flashcardquiz

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flashcardquiz.screens.EnterQuestionsScreen
import com.example.flashcardquiz.screens.QuizHistoryScreen
import com.example.flashcardquiz.screens.SolveQuizScreen
import com.example.flashcardquiz.viewmodel.FlashcardViewModel

@Composable
fun FlashcardQuizApp() {
    val navController = rememberNavController()
    val viewModel: FlashcardViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "enterQuestions") {
        composable("enterQuestions") {
            EnterQuestionsScreen(
                onStartQuiz = { navController.navigate("solveQuiz") },
                onViewHistory = { navController.navigate("quizHistory") },
                viewModel = viewModel
            )
        }
        composable("solveQuiz") {
            SolveQuizScreen(
                onQuizFinished = { navController.navigate("quizHistory") },
                viewModel = viewModel
            )
        }
        composable("quizHistory") {
            QuizHistoryScreen(
                onBackToQuestions = { navController.navigate("enterQuestions") },
                viewModel = viewModel
            )
        }
    }
}
