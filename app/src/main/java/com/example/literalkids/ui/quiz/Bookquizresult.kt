package com.example.literalkids.ui.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import com.example.literalkids.viewmodel.QuizViewModel
import android.util.Log

@Composable
fun QuizResultScreen(
    navController: NavController,
    viewModel: QuizViewModel = viewModel(navController.getBackStackEntry(Screen.Quiz.route))
) {
    val uiState by viewModel.uiState.collectAsState()
    Log.d("QuizResultScreen", "Displaying - Total Score: ${uiState.totalScore}, Total Coins: ${uiState.totalCoins}")

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_quiz_result),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp)
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
                        navController.popBackStack()
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
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.robot),
                    contentDescription = "Robot",
                    modifier = Modifier
                        .size(180.dp)
                        .offset(y = 0.dp)
                )

                Column(
                    modifier = Modifier
                        .background(color = Color(0xFFF3F1FD), shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .widthIn(min = 280.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        text = "Yeay Quiz Selesai!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF0A5470)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.star_badge),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "+${uiState.totalScore} XP",
                            fontWeight = FontWeight.Bold
                        )
                        Image(
                            painter = painterResource(id = R.drawable.coin),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "+${uiState.totalCoins} Koin",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Bagaimana cerita dan quiz sebelumnya?",
                        color = Color(0xFF0A5470),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FeedbackEmoji(
                            iconRes = R.drawable.smiley_seru,
                            label = "Seru",
                            isSelected = uiState.feedbackEmoji == "Seru",
                            onClick = { viewModel.selectFeedbackEmoji("Seru") }
                        )
                        FeedbackEmoji(
                            iconRes = R.drawable.smiley_lumayan,
                            label = "Lumayan",
                            isSelected = uiState.feedbackEmoji == "Lumayan",
                            onClick = { viewModel.selectFeedbackEmoji("Lumayan") }
                        )
                        FeedbackEmoji(
                            iconRes = R.drawable.smiley_tidak_menarik,
                            label = "Tidak Menarik",
                            isSelected = uiState.feedbackEmoji == "Tidak Menarik",
                            onClick = { viewModel.selectFeedbackEmoji("Tidak Menarik") }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.onFinishQuiz(navController) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AD8FF)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .width(200.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Kembali ke Profil",
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

@Composable
fun FeedbackEmoji(iconRes: Int, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier
                .size(48.dp)
                .clickable { onClick() }
                .background(
                    color = if (isSelected) Color(0xFF5AD8FF).copy(alpha = 0.2f) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}