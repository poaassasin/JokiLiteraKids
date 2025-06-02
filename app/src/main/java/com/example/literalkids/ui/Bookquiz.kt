package com.example.literalkids.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.literalkids.R
import com.example.literalkids.viewmodel.QuizViewModel
import android.util.Log
import com.example.literalkids.navigation.Screen

@Composable
fun QuizScreen(navController: NavController, viewModel: QuizViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    Log.d("QuizScreen", "Rendering - Total Score: ${uiState.totalScore}")
    if (uiState.questions.isEmpty()) {
        Text(
            text = "No questions loaded!",
            color = Color.Red,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
        return
    }
    val currentQuestion = uiState.questions[uiState.currentQuestionIndex]

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = currentQuestion.backgroundRes),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .align(Alignment.TopCenter)
                .padding(top = 1.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF))
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tombol_back),
                contentDescription = "Back",
                modifier = Modifier
                    .width(14.dp)
                    .height(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        navController.navigate(Screen.HomeBacaCerita.route)
                    }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = uiState.storyTitle,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 126.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
                    .align(Alignment.TopStart)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val cutSize = 20f

                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(width + cutSize, 0f)
                        lineTo(width, height / 2)
                        lineTo(width + cutSize, height)
                        lineTo(0f, height)
                        close()
                    }

                    drawPath(
                        path = path,
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF))
                        )
                    )
                }

                Text(
                    text = currentQuestion.questionLabel,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(end = 15.dp)
                )
            }

            if (currentQuestion.hasSound) {
                Image(
                    painter = painterResource(id = R.drawable.sound_quiz1),
                    contentDescription = "sound1",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .clickable { viewModel.onSoundClicked() }
                )
            }

            // Page indicator (posisi di tengah, ukuran kecil)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Center)
            ) {
                if (uiState.currentQuestionIndex > 0) {
                    Icon(
                        painter = painterResource(id = R.drawable.tombol_back),
                        contentDescription = "Previous",
                        tint = Color(0xFF5AD8FF),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { viewModel.goToPreviousQuestion() }
                    )
                } else {
                    Spacer(modifier = Modifier.width(20.dp))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(uiState.questions.size) { index ->
                        Image(
                            painter = painterResource(
                                id = if (index <= uiState.currentQuestionIndex) R.drawable.star_filled else R.drawable.star_empty
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(horizontal = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                if (uiState.currentQuestionIndex < uiState.questions.size - 1) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next",
                        tint = Color(0xFF5AD8FF),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { viewModel.goToNextQuestion() }
                    )
                } else {
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 16.dp, end = 16.dp, bottom = 190.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 355.dp, max = 355.dp)
                        .padding(2.dp)
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentQuestion.questionText,
                        fontSize = 22.sp,
                        color = Color(0xFF0A5470),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 1.dp, bottom = 25.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        userScrollEnabled = false
                    ) {
                        itemsIndexed(currentQuestion.answers) { index, answer ->
                            val isSelected = uiState.selectedAnswer == answer
                            val background = if (isSelected) {
                                Brush.horizontalGradient(colors = listOf(Color.Gray, Color.Gray))
                            } else {
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        when (index) {
                                            0 -> Color(0xFFCDF3F4)
                                            1 -> Color(0xFFEDF0FF)
                                            2 -> Color(0xFFF5D9FF)
                                            3 -> Color(0xFFFFF1B4)
                                            else -> Color.LightGray
                                        },
                                        when (index) {
                                            0 -> Color(0xFFCDF3F4)
                                            1 -> Color(0xFFEDF0FF)
                                            2 -> Color(0xFFF5D9FF)
                                            3 -> Color(0xFFFFF1B4)
                                            else -> Color.LightGray
                                        }
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(160.dp)
                                    .height(50.dp)
                                    .background(
                                        brush = background,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { viewModel.selectAnswer(answer) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = answer,
                                    fontSize = 14.sp,
                                    color = Color(0xFF0A5470),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentHeight(align = Alignment.CenterVertically)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.checkAnswer() },
                        enabled = uiState.selectedAnswer != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5AD8FF),
                            disabledContainerColor = Color.LightGray
                        ),
                        modifier = Modifier
                            .width(296.dp)
                            .height(46.dp)
                    ) {
                        Text(
                            text = "Cek Jawaban",
                            fontSize = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    if (uiState.showAnswer && uiState.selectedAnswer != null) {
                        Text(
                            text = uiState.answerFeedback,
                            fontSize = 12.sp,
                            color = if (uiState.isAnswerCorrect) Color(0xFF4CAF50) else Color.Red,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }

        if (uiState.showAnswer && uiState.selectedAnswer != null) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .padding(top = 24.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isAnswerCorrect) "Jawaban: Benar" else "Jawaban: Salah",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A5470),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(
                        painter = painterResource(id = if (uiState.isAnswerCorrect) R.drawable.ceklis_quiz1 else R.drawable.incorrect_quiz3),
                        contentDescription = if (uiState.isAnswerCorrect) "Benar" else "Salah",
                        tint = if (uiState.isAnswerCorrect) Color(0xFF4AA363) else Color(0xFFD12828),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = currentQuestion.correctAnswerExplanation,
                    fontSize = 14.sp,
                    color = Color(0xFF0A5470)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.isAnswerCorrect) { // Hanya muncul jika jawaban benar
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.star_badge),
                                contentDescription = "XP",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "+${uiState.totalScore} XP",
                                fontSize = 14.sp,
                                color = Color(0xFF0A5470),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(0.dp))
                    }

                    Button(
                        onClick = {
                            Log.d("QuizScreen", "Before Continue - Total Score: ${uiState.totalScore}, Total Coins: ${uiState.totalCoins}")
                            viewModel.onContinueClicked(navController)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AD8FF)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .height(45.dp)
                            .width(100.dp)
                    ) {
                        Text(
                            text = if (uiState.currentQuestionIndex < uiState.questions.size - 1) "Lanjut" else "Selesai",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}