package com.example.flashcardquiz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcardquiz.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolveQuizScreen(
    onQuizFinished: () -> Unit,
    viewModel: FlashcardViewModel= hiltViewModel()
) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }

    val flashcards = viewModel.flashcards

    if (flashcards.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0C042E))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "No flashcards available.",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
    } else if (currentQuestionIndex < flashcards.size) {
        val flashcard = flashcards[currentQuestionIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0C042E))
                .padding(16.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        "Quiz",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0C042E)),
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF387FF5)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = flashcard.question,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Your Answer") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (userAnswer.isNotBlank()) {
                            viewModel.checkAnswer(currentQuestionIndex, userAnswer)
                            currentQuestionIndex++
                            userAnswer = ""
                            showResult = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                        Color(0xFF543EE9)
                    )
                ) {
                    Text("Submit Answer", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showResult = !showResult },
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                        Color(0xFF543EE9)
                    )
                ) {
                    Text(if (showResult) "Hide Answer" else "Show Answer", color = Color.White)
                }
                if (showResult) {
                    Text(
                        text = "Correct answer: ${flashcard.answer}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0C042E))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Quiz Completed!",
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Your score: ${viewModel.score}", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.saveQuizResult()
                    onQuizFinished()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(Color(0xFF543EE9))
            ) {
                Text("View Results", color = Color.White)
            }
        }
    }
}
