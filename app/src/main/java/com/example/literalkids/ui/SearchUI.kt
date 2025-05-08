package com.example.literalkids.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen

@Composable
fun SearchUI(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    val searchHistory = remember { mutableStateListOf<String>() }


    val genres = listOf(
        "Dongeng" to R.drawable.dongeng,
        "Sains" to R.drawable.sains,
        "Fabel" to R.drawable.fabel,
        "Petualangan" to R.drawable.petualangan,
        "Komedi" to R.drawable.komedi,
        "Legenda" to R.drawable.legenda,
        "Mitos" to R.drawable.mitos,
        "Slice Of Life" to R.drawable.sliceoflife
    )

    val regions = listOf(
        "Sumatera" to R.drawable.sumatera,
        "Sulawesi" to R.drawable.sulawesi,
        "Jawa" to R.drawable.jawa,
        "Kalimantan" to R.drawable.kalimantan,
        "Papua" to R.drawable.papua,
        "Bali" to R.drawable.bali,
        "Nusa Tenggara" to R.drawable.nusatenggara
    )
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF7BDDFB), Color(0xFFD7A5FF))
    )

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
                    painter = painterResource(id = R.drawable.search_button), // ganti sesuai ikonmu
                    contentDescription = "Search",
                    tint = Color(0xFF7BDDFB),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = Color.Black), // Menentukan warna teks
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (searchQuery.isEmpty()) {
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
                    modifier = Modifier.clickable{ navController.popBackStack()}

                )
            }
        }

        Text("\nPencarian Terakhir", fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.height(100.dp)) {
            items(searchHistory) {
                Text("🔍 $it")
            }
        }

        Text("\nGenre Favorit", fontWeight = FontWeight.Bold)
        Column {
            for (row in genres.chunked(4)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    row.forEach { (genre, icon) ->
                        GenreItem(genre = genre, icon = icon) {
                            navController.navigate(Screen.GenreSelector.createRoute(genre))
                        }
                    }
                }
            }
        }

        Text("\nCerita Nusantara", fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(regions) { (region, image) ->
                RegionItem(region = region, image = image) {
                    navController.navigate("region/$region")
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
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = icon), contentDescription = genre, modifier = Modifier.size(40.dp))
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
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFDE99FF), Color(0xFF5AD8FF))
                )
            )
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxHeight()

        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = region,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp) // Lebar gambar, bisa disesuaikan
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



@Preview(showBackground = true)
@Composable
fun SearchUIPreview() {
    val dummyNavController = rememberNavController()
    SearchUI(navController = dummyNavController)
}