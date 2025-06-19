package com.example.literalkids.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ParentActivityScreen(navController: NavController, viewModel: ParentActivityViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header Gradien
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFF56CCF2), Color(0xFFB36FF1))
                    )
                )
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Orang Tua", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Detail Aktivitas",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A4A5A)
        )

        // Dropdown + BarChart
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Tampilkan Berdasarkan", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    Button(
                        onClick = { viewModel.toggleDropdownMenu() },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF76D0FF))
                    ) {
                        Text(viewModel.selected.value, color = Color.White)
                    }

                    DropdownMenu(expanded = viewModel.expanded.value, onDismissRequest = { viewModel.toggleDropdownMenu() }) {
                        listOf("Harian", "Mingguan", "Bulanan").forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = { viewModel.onDropdownMenuItemSelected(it) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Hari ini, si kecil telah membaca selama",
                    color = Color(0xFF1A4A5A)
                )
                Text(
                    "1 jam 28 menit",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB36FF1)
                )

                Spacer(modifier = Modifier.height(16.dp))
                BarChart(data = viewModel.data, labels = viewModel.labels)
            }
        }

        // Donut Charts
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DonutChart(percent = 0.68f, centerText = "68%")
            DonutChart(percent = 0.85f, centerText = "17\nCerita")
        }

        // Artikel Section
        ArticleSection(navController)
    }
}

@Composable
fun BarChart(data: List<Float>, labels: List<String>) {
    val barColor = Color(0xFF76D0FF)
    val highlightColor = Color(0xFFB36FF1)

    Column {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .width(20.dp)
                        .height((100 * value).dp)
                        .background(
                            if (index == 5) highlightColor else barColor,
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            labels.forEach { day ->
                Text(day, fontSize = 12.sp, color = Color(0xFF8A8A8A))
            }
        }
    }
}

@Composable
fun DonutChart(percent: Float, centerText: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(140.dp) // Set the size for the donut chart container
    ) {
        Canvas(modifier = Modifier.size(120.dp)) {
            // Draw the background arc (gray color, full circle)
            drawArc(
                color = Color(0xFFE0E0E0),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 24f, cap = StrokeCap.Round) // Set the stroke width
            )

            // Draw the actual donut (colored section based on percentage)
            drawArc(
                color = Color(0xFF76D0FF),
                startAngle = -90f, // Start from the top (12 o'clock position)
                sweepAngle = percent * 360f, // Sweep the angle based on the percentage
                useCenter = false,
                style = Stroke(width = 24f, cap = StrokeCap.Round) // Set the stroke width
            )
        }

        // Center text
        Text(
            centerText,
            fontSize = 18.sp, // Font size of the center text
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB36FF1), // Set the text color
            lineHeight = 22.sp, // Line height for spacing
            textAlign = androidx.compose.ui.text.style.TextAlign.Center // Center-align the text
        )
    }
}

@Composable
fun ArticleSection(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Artikel",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A2B50)
            ),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Menggunakan gambar yang berbeda untuk artikel pertama dan kedua
            ArticleCard(
                title = "Bagaimana Membiasakan Anak Membaca Setiap Hari?",
                category = "Kebiasaan",
                description = "Membaca setiap hari bisa menjadi tantangan bagi anak...",
                navController = navController,
                imageResId = R.drawable.anak, // Gambar pertama
                articleId = 1 // ID artikel pertama
            )
            ArticleCard(
                title = "Waktu Membaca Terfavorit Anak",
                category = "Tips Parenting",
                description = "Mengetahui waktu membaca terfavorit anak dapat membantu...",
                navController = navController,
                imageResId = R.drawable.ibuu, // Gambar kedua
                articleId = 2 // ID artikel kedua
            )
        }
    }
}

@Composable
fun ArticleCard(
    title: String,
    category: String,
    description: String,
    navController: NavController,
    imageResId: Int, // Parameter baru untuk mengganti gambar
    articleId: Int // Parameter untuk menentukan artikel mana yang diklik
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(260.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column {
            // Mengatur gambar agar proporsional dan konsisten
            Box(
                modifier = Modifier
                    .height(140.dp) // Set height yang konsisten dan lebih besar
                    .fillMaxWidth() // Mengisi lebar penuh
            ) {
                Image(
                    painter = painterResource(id = imageResId), // Gambar berbeda berdasarkan parameter
                    contentDescription = category,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Memastikan gambar crop dengan proporsional
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = category, fontSize = 12.sp, color = Color.Gray)

                // Teks judul artikel menjadi clickable
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    modifier = Modifier
                        .clickable {
                            // Navigasi berdasarkan articleId
                            if (articleId == 1) {
                                navController.navigate(Screen.ArticleDetail.route) // Artikel 1 navigasi ke ArticleDetailScreen
                            } else {
                                navController.navigate(Screen.ArticleDetail2.route) // Artikel 2 navigasi ke ArticleDetailScreen2
                            }
                        }
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewParentActivityScreen() {
    ParentActivityScreen(navController = rememberNavController())
}