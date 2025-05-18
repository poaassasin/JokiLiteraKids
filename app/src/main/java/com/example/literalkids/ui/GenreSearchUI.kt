package com.example.literalkids.ui

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.literalkids.R
import com.example.literalkids.viewmodel.GenreSearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.literalkids.viewmodel.StoryItem

@Composable
fun GenreSearchUI(
    navController: NavHostController,
    viewModel: GenreSearchViewModel = viewModel()
) {
    Log.d("NAV_CHECK", "Masuk ke GenreSearchUI")
    val uiState by viewModel.uiState.collectAsState()

    Column {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(colors = listOf(Color(0xFF69D3FF), Color(0xFFB39DDB)))
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
                        .clickable { navController.popBackStack() }
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
            uiState.genres.forEach { genre ->
                Text(
                    text = genre,
                    color = if (genre == uiState.selectedGenre) Color.White else Color(0xFFE0F7FA),
                    fontWeight = if (genre == uiState.selectedGenre) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable { viewModel.selectGenre(genre) }
                )
            }
        }

        // List Cerita
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.error}", color = Color.Red)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.stories) { story ->
                    StoryCard(story)
                }
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
            .border(width = 1.dp, color = Color.LightGray, shape = MaterialTheme.shapes.medium)
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(id = story.imageRes),
                contentDescription = story.title,
                contentScale = ContentScale.Crop,
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
                verticalArrangement = Arrangement.SpaceBetween
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
                            .size(20.dp)
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