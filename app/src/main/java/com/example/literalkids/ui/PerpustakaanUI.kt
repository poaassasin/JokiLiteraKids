package com.example.literalkids.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import com.example.literalkids.viewmodel.LibraryViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LibraryScreen(navController: NavController, viewModel: LibraryViewModel = viewModel()) {
    Log.d("NAV_CHECK", "Masuk ke LibraryScreen")
    val uiState by viewModel.uiState.collectAsState()
    val gradientBackground = Brush.verticalGradient(colors = listOf(Color(0xFF7BDDFB), Color(0xFFD7A5FF)))

    // State untuk dialog input nama koleksi
    var showDialog by remember { mutableStateOf(false) }
    var collectionName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp) // Cadangan ruang untuk tombol dan navbar
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
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.align(Alignment.Center)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { navController.navigate(Screen.Search.route) }
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

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}", color = Color.Red)
                }
            } else {
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

                // List Rak (Scrollable)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 360.dp), // Maksimal 3 rak tanpa scroll
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(uiState.shelves) { shelf ->
                        StoryShelf(
                            title = shelf.title,
                            storyCount = shelf.storyCount,
                            painter = painterResource(id = shelf.coverImage),
                            isEmpty = shelf.storyCount == 0,
                            onAddClicked = {
                                viewModel.addStoryToShelf(shelf.id)
                                navController.navigate(Screen.Search.route)
                            }
                        )
                    }
                }
            }
        }

        // Tombol Koleksi dan Bottom Navigation
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .zIndex(1f), // Pastikan di atas LazyColumn
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tombol Buat Koleksi Baru
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable { showDialog = true },
                color = Color(0xFF00BFFF),
                tonalElevation = 0.dp
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.shelves.any { it.isCustom }) "Kelola Koleksi" else "+ Buat koleksi cerita baru",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Navigation
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

    // Dialog untuk input nama koleksi
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nama Koleksi Baru", fontWeight = FontWeight.Bold) },
            text = {
                BasicTextField(
                    value = collectionName,
                    onValueChange = { collectionName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF6F6F6), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    decorationBox = { innerTextField ->
                        Box {
                            if (collectionName.isEmpty()) {
                                Text("Masukkan nama koleksi...", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.createNewCollection(collectionName.ifEmpty { "Koleksi Baru" })
                        collectionName = ""
                        showDialog = false
                    }
                ) {
                    Text("Buat", color = Color(0xFF00BFFF))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal", color = Color.Gray)
                }
            }
        )
    }
}

@Composable
fun StoryShelf(
    title: String,
    storyCount: Int,
    painter: Painter,
    isEmpty: Boolean,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFCCCCCC), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add_button),
                            contentDescription = "Tambah",
                            modifier = Modifier.size(24.dp)
                        )
                        if (isEmpty) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tambah Cerita",
                                fontSize = 12.sp,
                                color = Color(0xFF0066AA),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
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