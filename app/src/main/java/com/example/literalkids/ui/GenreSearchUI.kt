package com.example.literalkids.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R

data class StoryItem(
    val title: String,
    val genre: String,
    val description: String,
    val imageRes: Int,
    val reads: String,
    val isFavorite: Boolean = false
)

@Composable
fun GenreSearchUI(
    navController: NavHostController
) {
    val genres = listOf(
        "DONGENG", "SAINS", "FABEL", "PETUALANGAN",
        "KOMEDI", "LEGENDA", "MITOS", "SLICE OF LIFE"
    )
    var selectedGenre by remember { mutableStateOf("DONGENG") }

    val sampleStories = listOf(
        StoryItem("Ayam Jago Yang Sombong", "Komedi", "Seekor ayam jago merasa paling hebat di kandang...", R.drawable.ayam_jago, "3,8 Ribu"),
        StoryItem("Putri Embun Dan Pelangi", "Komedi", "Putri embun tinggal di langit dan hanya muncul saat pagi...", R.drawable.putri_embun, "4,1 Ribu"),
        StoryItem("Si Kura-Kura Pemalas", "Komedi", "Kura-kura lebih suka tidur daripada bekerja...", R.drawable.kura, "7,2 Ribu"),
        StoryItem("Naga Penjaga Gunung", "Komedi", "Seekor naga besar tinggal di puncak gunung...", R.drawable.naga, "6,5 Ribu"),
        StoryItem("Petualangan Tikus Dan Gajah", "Komedi", "Tikus kecil membantu gajah besar dari jebakan...", R.drawable.tikus_gajah, "5,5 Ribu")
    )

    Column {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF69D3FF), Color(0xFFB39DDB))
                    )
                )
                .padding(top = 24.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tombol_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Genre",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.size(24.dp)) // Placeholder
            }
        }

        // Genre Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF69D3FF))
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 12.dp, horizontal = 8.dp)
        ) {
            genres.forEach { genre ->
                Text(
                    text = genre,
                    color = if (genre == selectedGenre) Color.White else Color(0xFFE0F7FA),
                    fontWeight = if (genre == selectedGenre) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable {
                            selectedGenre = genre
                        }
                )
            }
        }

        // List Cerita
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleStories) { story ->
                StoryCard(story)
            }
        }
    }
}

@Composable
fun StoryCard(story: StoryItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(12.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray, // atau ganti warna sesuai keinginan
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier.fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = story.imageRes),
                contentDescription = story.title,
                contentScale = ContentScale.Crop, // pastikan gambar tidak stretched
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween // meratakan isi kolom secara vertikal
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = story.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1A237E),
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(id = if (story.isFavorite) R.drawable.like else R.drawable.like),
                        contentDescription = "Favorite",
                        tint = Color(0xFFBA68C8),
                        modifier = Modifier
                            .size(20.dp) // ukuran lebih kecil agar tidak melebihi ruang
                            .padding(end = 4.dp)
                    )
                }

                Text(
                    text = story.genre,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Text(
                    text = story.description,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 8.sp),
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${story.reads} Dibaca",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF0097A7),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GenreSearchUIPreview() {
    val dummyNavController = rememberNavController()
    GenreSearchUI(navController = dummyNavController)
}
