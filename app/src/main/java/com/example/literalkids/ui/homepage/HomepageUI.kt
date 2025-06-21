package com.example.literalkids.ui.homepage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.literalkids.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.example.literalkids.navigation.Screen
import com.example.literalkids.viewmodel.HomepageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.literalkids.ui.navbar.BottomNavigation

@Composable
fun HomepageUI(
    navController: NavController,
    viewModel: HomepageViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val primaryTextColor = Color(0xFF0A5470)
    val gradientColors = Brush.horizontalGradient(listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF)))

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: ${uiState.error}", color = Color.Red)
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Konten yang bisa discroll
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF5AD8FF),
                            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.robot_icon),
                                contentDescription = "Robot Icon",
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Halo, ${uiState.user?.name ?: "Pengguna"}!",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    "Siap mengeksplor cerita hari ini?",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .width(330.dp)
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 10.dp)
                                    .clickable { navController.navigate(Screen.Search.route) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "Search",
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cari cerita yang kamu mau...", color = Color.Gray)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hero Slider Section
                HeroSliderSection(uiState.bannerImages)

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Section
                uiState.user?.let { user ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(brush = gradientColors)
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Progres Bacamu Saat Ini", fontWeight = FontWeight.Bold, color = Color.White)
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(24.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(Color.White)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(user.progress)
                                            .clip(RoundedCornerShape(50))
                                            .background(
                                                Brush.horizontalGradient(
                                                    colorStops = arrayOf(
                                                        0.0f to Color(0xFFDE99FF),
                                                        0.8f to Color(0xFF855C99)
                                                    )
                                                )
                                            )
                                    )
                                    Text(
                                        text = "${(user.progress * 100).toInt()}%",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Text("Yuk baca lebih banyak cerita untuk capai target bulananmu!", fontSize = 12.sp, color = Color.White)
                            }
                            Image(
                                painter = painterResource(id = R.drawable.robot_megaphone),
                                contentDescription = "Robot Progress",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Papan Peringkat
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Papan Peringkat",
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor,
                        fontSize = 14.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { navController.navigate(Screen.Leaderboard.route) }
                    )
                }
                Text(
                    "Yuk, Asah Terus Minat Bacamu Dan Jadi Yang Terbaik",
                    color = Color(0xFFB0B0B0),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                uiState.user?.let { user ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(brush = gradientColors)
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar),
                                contentDescription = "Avatar",
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(user.name, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(user.username, fontSize = 12.sp, color = Color.White)
                            }
                            Image(
                                painter = painterResource(id = R.drawable.ic_crown_white),
                                contentDescription = "Crown",
                                modifier = Modifier.padding(horizontal = 4.dp).size(20.dp)
                            )
                            Text("Level ${user.level}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lanjutkan Cerita
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Yuk Lanjutkan Ceritamu!",
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor,
                        fontSize = 14.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    items(uiState.continueStories) { story ->
                        StoryCard(
                            imageRes = story.imageRes,
                            title = story.title,
                            progress = story.progress,
                            onClick = { navController.navigate(Screen.HomeBacaCerita.route) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Rekomendasi
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Rekomendasi Untuk Yang Suka Si Kancil",
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor,
                        fontSize = 14.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { navController.navigate(Screen.Search.route) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    items(uiState.recommendedStories) { story ->
                        StoryThumb(
                            imageRes = story.imageRes,
                            category = story.category,
                            title = story.title,
                            views = story.views,
                            onClick = { navController.navigate("storyReader/${story.id}") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Paling Populer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Paling Populer",
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor,
                        fontSize = 14.sp
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { navController.navigate(Screen.Search.route) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    items(uiState.popularStories) { story ->
                        StoryThumb(
                            imageRes = story.imageRes,
                            category = story.category,
                            title = story.title,
                            views = story.views,
                            onClick = { navController.navigate("storyReader/${story.id}") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Bottom Navigation (tetap di bawah, tidak ikut scroll)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomNavigation(
                    currentRoute = Screen.Homepage.route,
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
}

@Composable
fun StoryCard(imageRes: Int, title: String, progress: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .border(1.dp, Color(0xFFD5D5D5), shape = RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A5470),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFE7E7E7)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF5AD8FF), Color(0xFF368299))
                                )
                            )
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterStart)
                    )
                }
            }
        }
    }
}

@Composable
fun StoryThumb(
    imageRes: Int,
    category: String,
    title: String,
    views: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(end = 12.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
        border = BorderStroke(1.dp, Color(0xFFD5D5D5))
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    text = category,
                    fontSize = 10.sp,
                    color = Color(0xFF808080)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A5470)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = views,
                    fontSize = 10.sp,
                    color = Color(0xFF5AD8FF)
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeroSliderSection(bannerImages: List<Int>) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column {
        HorizontalPager(
            count = bannerImages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(20.dp))
        ) { page ->
            Image(
                painter = painterResource(id = bannerImages[page]),
                contentDescription = "Slider Image",
                modifier = Modifier.fillMaxSize()
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            activeColor = Color(0xFF5AD8FF),
            inactiveColor = Color(0xFFDFF5FF)
        )
    }
}