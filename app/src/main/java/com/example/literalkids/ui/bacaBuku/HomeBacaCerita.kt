package com.example.literalkids.ui.bacaBuku

import androidx.compose.foundation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import com.example.literalkids.viewmodel.HomeBacaCeritaViewModel

@Composable
fun HomeBacaCerita(navController: NavController, viewModel: HomeBacaCeritaViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val gradientBackground = Brush.horizontalGradient(
        colors = listOf(Color(0xFF7DE2FC), Color(0xFFB9B6FF))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBackground)
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tombol_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            navController.navigate(Screen.Homepage.route)
                        }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = uiState.storyTitle,
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                )
            }
        }

        // Bagian gambar & metadata
        Box {
            Image(
                painter = painterResource(id = R.drawable.kancil_ketimun),
                contentDescription = "Kancil Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                HeaderBadge(icon = Icons.Default.Book, text = uiState.readCount)
                HeaderBadge(icon = Icons.Default.FavoriteBorder, text = uiState.favoriteLabel)
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 32.dp)
            ) {
                Text(
                    text = uiState.genre,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = uiState.storyTitle,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        val capsuleShape = RoundedCornerShape(percent = 50)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            // XP Button
            Surface(
                shape = capsuleShape,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF7DE2FC), Color(0xFFB9B6FF))
                            ),
                            shape = capsuleShape
                        )
                        .padding(1.dp)
                ) {
                    Button(
                        onClick = { viewModel.onXpButtonClicked() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = capsuleShape,
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text(uiState.xpReward, color = Color(0xFF00475D), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Koin Button
            Surface(
                shape = capsuleShape,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF7DE2FC), Color(0xFFB9B6FF))
                            ),
                            shape = capsuleShape
                        )
                        .padding(1.dp)
                ) {
                    Button(
                        onClick = { viewModel.onCoinButtonClicked() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = capsuleShape,
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text(uiState.coinReward, color = Color(0xFF00475D), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Sinopsis",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.Gray
        )

        Text(
            text = uiState.synopsis,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color(0xFF00475D)
        )

        Spacer(modifier = Modifier.height(180.dp))

        Button(
            onClick = { navController.navigate(Screen.BacaCeritaScreen.route) },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C2FF))
        ) {
            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Baca Sekarang", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun HeaderBadge(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(50)
            )
            .border(1.dp, Color.White, shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, color = Color.White, fontSize = 12.sp)
    }
}