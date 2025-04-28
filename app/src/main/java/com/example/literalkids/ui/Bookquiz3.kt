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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R

@Composable
fun QuizScreen3(navController: NavHostController) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showAnswer by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_quiz3),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize()
        )

        // Header
        Box(
            modifier = Modifier
                .height(110.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 1.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF5AD8FF), // 0% position - #5AD8FF
                            Color(0xFFDE99FF)  // 100% position - #DE99FF
                        )
                    )
                )
        )

        // Title Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Tombol Back di sebelah kiri, tetap di dalam Row
            Image(
                painter = painterResource(id = R.drawable.tombol_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        // TODO: aksi kembali
                        println("Back clicked")
                    }
            )

            Spacer(modifier = Modifier.width(12.dp)) // Jarak antar icon dan text

            // Title
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
            // Soal Box
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp)
                    .align(Alignment.TopStart)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val cutSize = 20f // size of the cut triangle

                    val path = Path().apply {
                        moveTo(0f, 0f) // Start at the top left
                        lineTo(width + cutSize, 0f) // Top right before the cut
                        lineTo(width, height / 2) // Cut point
                        lineTo(width + cutSize, height) // Bottom right before the cut
                        lineTo(0f, height) // Bottom left
                        close() // Close the path
                    }

                    drawPath(
                        path = path,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5AD8FF), // Blue
                                Color(0xFFDE99FF)  // Light Purple
                            )
                        )
                    )
                }

                Text(
                    text = "Soal 1",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(end = 15.dp)
                )
            }

            // Sound Image
            Image(
                painter = painterResource(id = R.drawable.sound_quiz1),
                contentDescription = "sound1",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        // TODO: Ganti dengan aksi seperti play suara
                        println("Tombol suara diklik!") // Bisa diganti dengan Log.d() atau pemutar audio
                    }
            )

            // Star Image
            StarRating(2)
        }

        // Question and Answer UI
        // Question and Answer UI
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
//                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Apa yang membuat Pak Tani marah?",
                        fontSize = 22.sp,
                        color = Color(0xFF0A5470),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 36.dp, bottom = 25.dp, start = 28.dp, end = 28.dp)
                    )
                    val answers = listOf(
                        "Kancil Minta Ketimun",
                        "Kancil Mencuri Ketimun",
                        "Kancil Tidur Di Kebun",
                        "Kancil Membantu Di Kebun"
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 28.dp),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        userScrollEnabled = false
                    ) {
                        items(answers) { answer ->
                            val isSelected = selectedAnswer == answer
                            val bgColor = when {
                                answer.contains("Minta") -> Color(0xFFCDF3F4)
                                answer.contains("Mencuri") -> Color(0xFFEDF0FF)
                                answer.contains("Tidur") -> Color(0xFFF5D9FF)
                                answer.contains("Membantu") -> Color(0xFFFFF1B4)
                                else -> Color.LightGray
                            }
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(140.dp)
                                    .height(45.dp)
                                    .background(
                                        color = if (isSelected) Color.Gray else bgColor,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedAnswer = answer }
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = answer,
                                    fontSize = 12.sp,
                                    color = Color(0xFF0A5470),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    lineHeight = 16.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
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
                        val correctAnswer = "Kancil Mencuri Ketimun"
                        val isCorrect = selectedAnswer == correctAnswer
                        val answerText = if (isCorrect) {
                            "Jawaban: Benar \nPak Tani Marah Karena Kancil Mencuri Ketimun."
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

        // ✅ Bottom Info Panel
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
            // ✅ Jawaban: Salah + Icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jawaban: Salah",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A5470),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.incorrect_quiz3),
                    contentDescription = "Salah",
                    tint = Color(0xFF0A5470)
                )
            }

            // ✅ Penjelasan
            Text(
                text = "dari cerita si kancil, kita bisa belajar bahwa kita harus selalu bersikap jujur dan tidak pernah mencuri.",
                fontSize = 14.sp,
                color = Color(0xFF0A5470),
                textAlign = TextAlign.Start
            )

            // ✅ Baris bawah: XP + Tombol
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ✅ Tombol Lanjut
                Button(
                    onClick = { showAnswer = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AD8FF)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(45.dp)
                        .width(120.dp)
                ) {
                    Text(
                        text = "Selesai",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreen3Preview() {
    MaterialTheme {
        val navController = rememberNavController()
        QuizScreen3(navController = navController)
    }
}
