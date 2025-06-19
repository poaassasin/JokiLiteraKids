package com.example.literalkids.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R

@Composable
fun ArticleDetailScreen(navController: NavController, viewModel: ArticleDetailViewModel = viewModel()) {
    val comments = viewModel.comments

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header Artikel
        ArticleHeader(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Artikel Content dengan padding di samping
        ArticleContent()

        Spacer(modifier = Modifier.height(24.dp))

        // Komentar Section
        CommentSection(comments = comments)
    }
}

@Composable
fun ArticleHeader(navController: NavController) {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Tombol back di kiri
            IconButton(
                onClick = { navController.popBackStack() }, // Navigasi kembali ke ParentActivityScreen
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            // Teks judul artikel digeser ke kanan sedikit
            Text(
                "Bagaimana Membiasakan Anak Membaca Setiap Hari?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ArticleContent() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)  // Menambahkan padding kiri dan kanan
    ) {
        // Gambar artikel menempel di bawah header gradien
        Image(
            painter = painterResource(id = R.drawable.anak),  // Ganti dengan id gambar artikel sesuai
            contentDescription = "Article Image",
            modifier = Modifier
                .fillMaxWidth() // Mengisi seluruh lebar
                .height(180.dp)
                .clip(RoundedCornerShape(0.dp)) // Menghapus pembulatan agar menempel langsung
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Teks kategori artikel
        Text(
            text = "Kebiasaan",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1A4A5A)
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Teks judul artikel
        Text(
            text = "Bagaimana Membiasakan Anak Membaca Setiap Hari?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A4A5A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Artikel body
        Text(
            text = "Membiasakan anak membaca setiap hari memang bukan hal yang instan. Perlu pendekatan yang menyenangkan agar kegiatan membaca terasa seperti waktu bermain, bukan kewajiban. Salah satu caranya adalah dengan memilih buku yang sesuai minat dan usia anak. Cerita bergambar dengan tokoh lucu atau dongeng penuh imajinasi bisa menarik perhatian anak dan membuatnya antusias membaca halaman demi halaman.\n\n" +
                    "Orang tua juga bisa menjadikan membaca sebagai rutinitas harian, seperti sebelum tidur atau setelah makan siang. Bacalah bersama dan beri ruang bagi anak untuk ikut bercerita ulang. Aktivitas ini tidak hanya membangun kebiasaan membaca, tetapi juga mempererat hubungan emosional antara anak dan orang tua. Ingat, kunci utamanya adalah konsistensi dan suasana yang menyenangkan!",
            fontSize = 16.sp,
            color = Color(0xFF1A4A5A),
            lineHeight = 24.sp
        )
    }
}

@Composable
fun CommentSection(comments: List<Comment>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Judul bagian komentar
        Text(
            text = "Komentar",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A4A5A),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        comments.forEach { comment ->
            CommentItem(comment = comment)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Comment input field dengan padding kiri dan kanan
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), // Padding kiri dan kanan
            label = { Text("Tulis komentar...") },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF76D0FF),
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Padding kiri dan kanan
    ) {
        // Nama dan waktu komentar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = comment.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A4A5A)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = comment.time,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Teks komentar
        Text(
            text = comment.comment,
            fontSize = 16.sp,
            color = Color(0xFF1A4A5A),
            lineHeight = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Like & Dislike Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${comment.likes} 👍",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "${comment.dislikes} 👎",
                fontSize = 14.sp,
                color = Color.Gray
            )
            TextButton(onClick = { /* Handle reply action */ }) {
                Text(
                    text = "Balas",
                    fontSize = 14.sp,
                    color = Color(0xFF76D0FF)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArticleDetailScreen() {
    val navController = rememberNavController()
    ArticleDetailScreen(navController = navController)
}