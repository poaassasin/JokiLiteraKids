package com.example.literalkids.ui.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.literalkids.R
import com.example.literalkids.viewmodel.SearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchUI(navController: NavHostController, viewModel: SearchViewModel = viewModel()) {
    Log.d("NAV_CHECK", "Masuk ke SearchUI")
    val uiState by viewModel.uiState.collectAsState()
    val gradientBackground = Brush.verticalGradient(colors = listOf(Color(0xFF7BDDFB), Color(0xFFD7A5FF)))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBackground)
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_button),
                    contentDescription = "Search",
                    tint = Color(0xFF7BDDFB),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = Color.Black),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (uiState.searchQuery.isEmpty()) {
                                Text(
                                    text = "Cari cerita yang kamu mau...",
                                    color = Color.Gray,
                                    style = TextStyle(fontSize = 16.sp)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Batalkan",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${uiState.error}", color = Color.Red)
            }
        } else {
            // Pencarian Terakhir
            Text(
                text = "\nPencarian Terakhir",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(modifier = Modifier.height(100.dp)) {
                items(uiState.searchHistory) { query ->
                    Text(
                        text = "🔍 $query",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }

            // Genre Favorit
            Text(
                text = "\nGenre Favorit",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Column {
                for (row in uiState.genres.chunked(4)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach { genre ->
                            GenreItem(
                                genre = genre.name,
                                icon = genre.icon,
                                onClick = { viewModel.navigateToGenre(navController, genre.name) }
                            )
                        }
                    }
                }
            }

            // Cerita Nusantara
            Text(
                text = "\nCerita Nusantara",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(uiState.regions) { region ->
                    RegionItem(
                        region = region.name,
                        image = region.icon,
                        onClick = { viewModel.navigateToRegion(navController, region.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun GenreItem(genre: String, icon: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(colors = listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF)))
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = genre,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = genre, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun RegionItem(region: String, image: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(colors = listOf(Color(0xFFDE99FF), Color(0xFF5AD8FF)))
            )
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = region,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = region,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}