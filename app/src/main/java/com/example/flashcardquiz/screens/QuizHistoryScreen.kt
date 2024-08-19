package com.example.flashcardquiz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcardquiz.viewmodel.FlashcardViewModel

@Composable
fun QuizHistoryScreen(
    onBackToQuestions: () -> Unit,
    viewModel: FlashcardViewModel = hiltViewModel()
) {
    val history = viewModel.quizHistory
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C042E))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Quiz History",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (history.isEmpty()) {
            Text(
                text = "No quiz history available",
                color = Color.Gray,
                style = MaterialTheme.typography.body1
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(history) { quizResult ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF387FF5)),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Score: ${quizResult.score}",
                                style = MaterialTheme.typography.body1,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Date: ${quizResult.date}",
                                style = MaterialTheme.typography.body2,
                                color = Color.White,
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBackToQuestions,
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.textButtonColors(Color(0xFF543EE9))
        ) {
            Text("Back to Questions", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

