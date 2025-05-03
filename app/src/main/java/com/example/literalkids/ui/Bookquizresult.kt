package com.example.literalkids.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R

@Composable
fun QuizResultScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background
        Image(
            painter = painterResource(id = R.drawable.bg_quiz_result),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Header
        Box(
            modifier = Modifier
                .height(105.dp)
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
                    .width(14.dp)
                    .height(20.dp)
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

        // Card Layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            // Robot
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Robot mengambang di atas card
                Image(
                    painter = painterResource(id = R.drawable.robot),
                    contentDescription = "Robot",
                    modifier = Modifier
                        .size(180.dp)
                        .offset(y = 0.dp)
                )

                // Card utama
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFFF3F1FD), shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .widthIn(min = 280.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(60.dp)) // Biar ada jarak di atas karena robot
                    Text("Yeay Quiz Selesai!", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0A5470))
                    Spacer(modifier = Modifier.height(12.dp))

                    // XP dan Koin
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.star_badge), contentDescription = null, modifier = Modifier.size(40.dp))
                        Text("+40 XP", fontWeight = FontWeight.Bold)
                        Image(painter = painterResource(id = R.drawable.coin), contentDescription = null, modifier = Modifier.size(40.dp))
                        Text("+20 Koin", fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("Bagaimana cerita dan quiz sebelumnya?", color = Color(0xFF0A5470), fontSize = 14.sp, textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                        FeedbackEmoji(R.drawable.smiley_seru, "Seru")
                        FeedbackEmoji(R.drawable.smiley_lumayan, "Lumayan")
                        FeedbackEmoji(R.drawable.smiley_tidak_menarik, "Tidak Menarik")
                    }
                }
            }
        }
    }
}

@Composable
fun FeedbackEmoji(iconRes: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizResultScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        QuizResultScreen(navController = navController)
    }
}