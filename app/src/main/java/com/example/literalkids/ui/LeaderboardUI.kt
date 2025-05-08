package com.example.literalkids.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import kotlinx.coroutines.delay

data class LeaderboardUser(
    val id: String,
    val fullName: String,
    val username: String,
    val level: Int,
    val avatarUrl: Int,
    val isCurrentUser: Boolean = false
)

sealed class LeaderboardState {
    object Loading : LeaderboardState()
    data class Success(val users: List<LeaderboardUser>) : LeaderboardState()
    data class Error(val message: String) : LeaderboardState()
}

suspend fun getLeaderboardUsers(): List<LeaderboardUser> {

    delay(1000)

    val mockUsers = listOf(
        LeaderboardUser("user1", "Ayu Dewi", "ayudewi", 99, R.drawable.avatar_bunga), // parent
        LeaderboardUser("user2", "Fahri Hidayat", "fahridayat", 90, R.drawable.avatar_dino), // parent
        LeaderboardUser("user3", "Lala Rumi", "lalamimi", 44, R.drawable.avatar_ksatria), // child
        LeaderboardUser("user4", "Salsabilla Putri", "salsabil", 41, R.drawable.avatar_poni),
        LeaderboardUser("user5", "Ahmad Farhan", "ahmadfar", 19, R.drawable.parent_avatar), // parent
        LeaderboardUser("user6", "Rafa Elvano", "rafaelvano", 37, R.drawable.avatar_bunga),
        LeaderboardUser("user7", "Anisa Lestari", "anisalestari", 21, R.drawable.avatar_ksatria), // parent
        LeaderboardUser("user8", "Budi Santoso", "budisantoso", 10, R.drawable.parent_avatar), // parent
        LeaderboardUser("user9", "Kalea Syakira", "inisyakira", 29, R.drawable.avatar_katak),
        LeaderboardUser("user10", "Fayra Alesha", "feyysha", 28, R.drawable.avatar_dino),
        LeaderboardUser("user11", "Levi Annora", "leviannora", 7, R.drawable.default_avatar, true),
        LeaderboardUser("user12", "Adinda Febyola", "febydinda", 38, R.drawable.parent_avatar)
    )

    return mockUsers.sortedByDescending { it.level }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardUI(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    navController: NavHostController
) {
    var leaderboardState by remember { mutableStateOf<LeaderboardState>(LeaderboardState.Loading) }

    LaunchedEffect(key1 = true) {
        try {
            val users = getLeaderboardUsers()
            leaderboardState = LeaderboardState.Success(users)
        } catch (e: Exception) {
            leaderboardState = LeaderboardState.Error("Error loading leaderboard: ${e.message}")
        }
    }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF58C6FA),
            Color(0xFFA892F3)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Papan Peringkat",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = modifier
                                .clickable { navController.navigate(Screen.Homepage.route)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(gradientBackground)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (leaderboardState) {
                is LeaderboardState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is LeaderboardState.Error -> {
                    Text(
                        text = (leaderboardState as LeaderboardState.Error).message,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                is LeaderboardState.Success -> {
                    val allUsers = (leaderboardState as LeaderboardState.Success).users

                    val currentUser = allUsers.find { it.isCurrentUser }
                    val currentUserPosition = allUsers.indexOfFirst { it.isCurrentUser } + 1

                    val topUsers = allUsers.take(10)

                    LeaderboardContentWithStickyUser(
                        allUsers = topUsers,
                        currentUser = currentUser,
                        currentUserPosition = currentUserPosition,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderboardContentWithStickyUser(
    allUsers: List<LeaderboardUser>,
    currentUser: LeaderboardUser?,
    currentUserPosition: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFF8F8F8),
                    Color.White
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFA892F3).copy(alpha = 0.3f),
                                Color(0xFFF8F8F8)
                            )
                        )
                    )
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Top 10",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF006699)
                )
                Text(
                    text = "Yuk, Asah Terus Minat Bacamu Dan Jadi Yang Terbaik!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 80.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    itemsIndexed(allUsers) { index, user ->
                        if (!user.isCurrentUser) {
                            LeaderboardItem(
                                user = user,
                                position = index + 1,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        currentUser?.let { user ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                LeaderboardItem(
                    user = user,
                    position = currentUserPosition,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun LeaderboardItem(
    user: LeaderboardUser,
    position: Int,
    modifier: Modifier = Modifier
) {
    val backgroundBrush = when (position) {
        1 -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFCF25),
                Color(0x4DFFCF25)
            )
        )
        2 -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFFB0B0B0),
                Color(0x4DB0B0B0)
            )
        )
        3 -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFFD57D3D),
                Color(0x4DD57D3D)
            )
        )
        else -> null
    } ?: if (user.isCurrentUser) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF5AD8FF),
                Color(0xFFDE99FF)
            )
        )
    } else {
        null
    }

    val backgroundColor = Color.White

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (backgroundBrush != null) {
                        Modifier.background(backgroundBrush)
                    } else {
                        Modifier
                    }
                )
                .padding(12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                if (position <= 3) {
                    val ringColor = when (position) {
                        1 -> Brush.radialGradient(
                            listOf(Color(0xFFFFCF25), Color(0x4DFFCF25)),
                            radius = 60f
                        )
                        2 -> Brush.radialGradient(
                            listOf(Color(0xFFB0B0B0), Color(0x4DB0B0B0)),
                            radius = 60f
                        )
                        else -> Brush.radialGradient(
                            listOf(Color(0xFFD57D3D), Color(0x4DD57D3D)),
                            radius = 60f
                        )
                    }
                    Canvas(
                        modifier = Modifier
                            .size(64.dp)
                            .zIndex(0f)
                    ) {
                        drawCircle(
                            brush = ringColor,
                            radius = size.minDimension / 2,
                            style = Stroke(width = 6f, cap = StrokeCap.Round)
                        )
                    }
                }
                Image(
                    painter = painterResource(id = user.avatarUrl),
                    contentDescription = "Avatar for ${user.fullName}",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .zIndex(1f)
                        .background(Color.White)
                )
                Surface(
                    shape = CircleShape,
                    color = when (position) {
                        1 -> Color(0xFFFFCF25)
                        2 -> Color(0xFFB0B0B0)
                        3 -> Color(0xFFD57D3D)
                        else -> if (user.isCurrentUser) Color(0xFFB173FF) else Color(0xFF78D9FF)
                    },
                    modifier = Modifier
                        .size(22.dp).then(
                            if (position <= 3) {
                                Modifier.offset((-2).dp, (-2).dp)
                            } else {
                                Modifier.offset((-6).dp, (-6).dp)
                            }
                        )
                        .align(Alignment.TopStart)
                        .zIndex(4f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = position.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = user.fullName,
                    fontWeight = FontWeight.Bold,
                    color = if (user.isCurrentUser) Color.White else Color(0xFF0A5470),
                    fontSize = 16.sp
                )
                Text(
                    text = "@${user.username}",
                    color = if (user.isCurrentUser) Color.White else Color(0xFF0A5470),
                    fontSize = 12.sp
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        id = if (position <= 3 || user.isCurrentUser) R.drawable.crown_white else R.drawable.crown_blue
                    ),
                    contentDescription = "Level icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Level ${user.level}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = if (user.isCurrentUser) Color.White else Color(0xFF0A5470),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}