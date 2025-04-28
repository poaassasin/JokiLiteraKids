package com.example.literalkids.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen

@Composable
fun QuizScreen2(navController: NavController) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showAnswer by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_quiz2),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize()
        )

        // Header
        Box(
            modifier = Modifier
                .width(412.dp)
                .height(110.dp)
                .align(Alignment.TopCenter)
                .padding(top = 1.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF5AD8FF),
                            Color(0xFFDE99FF)
                        )
                    )
                )
        )

        // Title Text with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Tombol Back
            Image(
                painter = painterResource(id = R.drawable.tombol_back),
                contentDescription = "Back",
                modifier = Modifier
                    .width(14.dp)
                    .height(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        navController.popBackStack() // Kembali ke screen sebelumnya
                    }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Kancil Mencuri Ketimun",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 126.dp, end = 16.dp)
        ) {
            // Box Soal
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
                    text = "Soal 2",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(end = 15.dp)
                )
            }

            // Tombol suara
            Image(
                painter = painterResource(id = R.drawable.sound_quiz1),
                contentDescription = "sound1",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        println("Tombol suara diklik!")
                    }
            )

            StarRating(2)
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
                        text = "Bagaimana cara Pak Tani menangkap kancil?",
                        fontSize = 22.sp,
                        color = Color(0xFF0A5470),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 1.dp, bottom = 25.dp)
                    )

                    val answers = listOf(
                        "Menggunakan Perangkap Jebakan",
                        "Mengejar Kancil Dengan Cepat",
                        "Memanggil Hewan Lain",
                        "Memasang Jaring"
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
                        items(answers) { answer ->
                            val isSelected = selectedAnswer == answer
                            val bgColor = when {
                                answer.contains("Perangkap") -> Color(0xFF8BC1C3)
                                answer.contains("Kancil") -> Color(0xFFEDF0FF)
                                answer.contains("Hewan") -> Color(0xFFF5D9FF)
                                answer.contains("Jaring") -> Color(0xFFFFF1B4)
                                else -> Color.LightGray
                            }

                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(160.dp)
                                    .height(50.dp)
                                    .background(
                                        color = if (isSelected) Color.Gray else bgColor,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { selectedAnswer = answer }
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
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
                                        .width(140.dp)
                                        .height(42.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showAnswer = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AD8FF)),
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

                    if (showAnswer && selectedAnswer != null) {
                        val correctAnswer = "Menggunakan Perangkap Jebakan"
                        val isCorrect = selectedAnswer == correctAnswer
                        val answerText = if (isCorrect) {
                            "Jawaban: Benar \nPak Tani Menangkap Kancil Dengan Perangkap."
                        } else {
                            "Jawaban: Salah"
                        }

                        Text(
                            text = answerText,
                            fontSize = 12.sp,
                            color = if (isCorrect) Color(0xFF4CAF50) else Color.Red,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }

        // Bottom Panel
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
                    text = "Jawaban: Benar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A5470),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ceklis_quiz1),
                    contentDescription = "Benar",
                    tint = Color(0xFF4AA363),
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Pak Tani Menangkap Kancil Dengan Cara Menggunakan Perangkap Jebakan.",
                fontSize = 14.sp,
                color = Color(0xFF0A5470)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_badge),
                        contentDescription = "XP",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+20 XP",
                        fontSize = 14.sp,
                        color = Color(0xFF0A5470),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.Quiz.route) // Ganti sesuai ke mana kamu ingin lanjut
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AD8FF)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(45.dp)
                        .width(100.dp)
                ) {
                    Text(
                        text = "Lanjut",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun StarRatingForQuiz2(score: Int, max: Int = 3) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..max).forEach { index ->
            val drawable = if (index <= score) R.drawable.star_filled else R.drawable.star_empty
            Image(
                painter = painterResource(id = drawable),
                contentDescription = "star$index",
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Image(
            painter = painterResource(id = R.drawable.next),
            contentDescription = "next1",
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreen2Preview() {
    MaterialTheme {
        val navController = rememberNavController()
        QuizScreen2(navController = navController)
    }
}
