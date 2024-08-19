package com.example.flashcardquiz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun EnterQuestionsScreen(
    onStartQuiz: () -> Unit,
    onViewHistory: () -> Unit,
    viewModel: FlashcardViewModel= hiltViewModel()
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C042E))
            .padding(16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    "Flashcard Quiz",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            backgroundColor = Color(0xFF0C042E),
            contentColor = Color.White
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                label = { Text("Enter Question", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                label = { Text("Enter Answer", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (question.isNotBlank() && answer.isNotBlank()) {
                        viewModel.addFlashcard(question, answer)
                        question = ""
                        answer = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(Color(0xFF543EE9))
            ) {
                Text("Add Flashcard", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onStartQuiz,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(Color(0xFF543EE9))
            ) {
                Text("Start Quiz", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onViewHistory,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.textButtonColors(Color(0xFF543EE9))
            ) {
                Text("View Quiz History", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                item {
                    Text(
                        text = "Questions",
                        Modifier.fillMaxWidth(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                items(viewModel.flashcards) { flashcard ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = Color.Cyan,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = flashcard.question,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}