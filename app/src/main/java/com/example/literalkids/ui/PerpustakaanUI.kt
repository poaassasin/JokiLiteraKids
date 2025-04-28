package com.example.literalkids.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen


@Composable
fun LibraryScreen(navController: NavController) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF7BDDFB), Color(0xFFD7A5FF))

    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header dengan tombol search dan judul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBackground)
                .padding(16.dp)
        ) {
            Text(
                text = "Perpustakaan",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { /* TODO: Search action */ }
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_button),
                    contentDescription = "Search",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Rak Cerita Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rak Cerita",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF333333)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List Rak
        StoryShelf(
            title = "Sedang Dibaca",
            initialStoryCount = 0,
            painter = painterResource(id = R.drawable.sample_sedang_dibaca),
            modifier = Modifier.padding(horizontal = 18.dp),
            onAddClicked = {
                navController.navigate(Screen.Search.route)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        StoryShelf(
            title = "Favorit",
            initialStoryCount = 0,
            painter = painterResource(id = R.drawable.sample_favorit),
            modifier = Modifier.padding(horizontal = 18.dp),
            onAddClicked = {
                navController.navigate(Screen.Search.route)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        StoryShelf(
            title = "Selesai Dibaca",
            initialStoryCount = 0,
            painter = painterResource(id = R.drawable.sample_selesai_dibaca),
            modifier = Modifier.padding(horizontal = 18.dp),
            onAddClicked = {
                navController.navigate(Screen.Search.route)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Tombol Buat Koleksi Baru
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .clickable { /* TODO: Buat koleksi baru */ },
            color = Color(0xFF00BFFF),
            tonalElevation = 0.dp
        ) {

            Box(
                modifier = Modifier.padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+ Buat koleksi cerita baru",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavigation(
                currentRoute = Screen.Perpustakaan.route,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Composable
fun StoryShelf(
    title: String,
    initialStoryCount: Int,
    painter: Painter,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var storyCount by remember { mutableStateOf(initialStoryCount) }
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp) // dalam card
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.5f))
                        .clickable { onAddClicked() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_button),
                        contentDescription = "Tambah",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0066AA)
                )
                Text(
                    text = "$storyCount Cerita",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    val navController = rememberNavController()

    // Menampilkan LibraryScreen dengan NavController
    LibraryScreen(navController = navController)
}

