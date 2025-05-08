package com.example.literalkids.ui

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen

data class CeritaPage(
    val title: String,
    val text: String,
    val backgroundRes: Int
)

@Composable
fun BacaCeritaScreenUI(navController: NavController, context: Context = LocalContext.current) {
    val ceritaPages = listOf(
        CeritaPage(
            "1 - Rasa Lapar Ditengah Hutan",
            "Si Kancil yang cerdik menyelinap ke kebun petani untuk mencuri ketimun segar. Namun, ia tertangkap jebakan yang dipasang oleh petani. Dengan akalnya, Kancil berpura-pura menyesal dan menipu petani hingga akhirnya berhasil melarikan diri.",
            R.drawable.bg_ilustrasi1
        ),
        CeritaPage(
            "2 - Rencana Diam-Diam",
            "Si Kancil Kancil tahu kebun itu milik Pak Tani. Tapi rasa lapar membuatnya nekat. Malam hari, saat hutan mulai sunyi, Kancil menyelinap masuk lewat celah pagar. Ia memetik satu ketimun besar dan melahapnya cepat-cepat. “Hmmm… manis dan segar!” katanya senang. Ia kembali ke hutan dengan perut kenyang. Besoknya, Kancil datang lagi, dan lagi. Ia bahkan mulai membawa teman-temannya diam-diam.",
            R.drawable.bg_ilustrasi2
        ),
        CeritaPage(
            "3 - Pak Tani Curiga",
            "Setiap pagi, Pak Tani merasa jumlah ketimunnya semakin berkurang. “Siapa yang mencuri hasil kebunku?” gerutunya. Lalu ia membuat rencana. Ia memasang jebakan dari jaring dan tali di tengah kebun, di sekitar ketimun paling besar. “Kalau pencuri datang lagi, pasti tertangkap!” ujar Pak Tani yakin.",
            R.drawable.bg_ilustrasi3
        ),
        CeritaPage(
            "4 - Tertangkap",
            "Malam pun tiba. Kancil kembali, kali ini sendirian. Ia melompat girang saat melihat ketimun favoritnya masih ada. “Ini pasti hadiah untukku,” katanya bangga. Tapi begitu ia melangkah... ZAPP! Kakinya terjerat jaring! Kancil panik dan berusaha melepaskan diri, tapi sia-sia. Ia terjebak hingga pagi.",
            R.drawable.bg_ilustrasi4
        ),
        CeritaPage(
            "5 - Belajar Dari Kesalahan",
            "Pak Tani datang membawa keranjang. Ia terkejut melihat seekor kancil kecil yang gemetar ketakutan. Namun Pak Tani tidak marah. “Kamu lapar, ya? Tapi mencuri itu tidak benar,” katanya lembut. Ia melepaskan Kancil, lalu berkata, “Lain kali, mintalah baik-baik.” Sejak saat itu, Kancil tak pernah mencuri lagi. Ia belajar mencari makanan dengan cara yang jujur dan mulai menanam ketimun sendiri.",
            R.drawable.bg_ilustrasi5
        )
    )

    var currentPage by remember { mutableStateOf(0) }
    val currentCerita = ceritaPages[currentPage]
    var isSpeaking by remember { mutableStateOf(false) }
    var showFinishDialog by remember { mutableStateOf(false) }

    val tts = remember {
        TextToSpeech(context) {}.apply {
            language = java.util.Locale("id", "ID")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background gambar
        Image(
            painter = painterResource(id = currentCerita.backgroundRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // memberi ruang untuk page indicator
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF7DE2FC), Color(0xFFB9B6FF))
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.tombol_back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.navigate(Screen.Profile.route) }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Kancil Mencuri Ketimun",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Banner Judul dan Tombol
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val width = size.width
                        val height = size.height
                        val cutSize = 20f

                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(width - cutSize, 0f)
                            lineTo(width, height / 2)
                            lineTo(width - cutSize, height)
                            lineTo(0f, height)
                            close()
                        }

                        drawPath(
                            path = path,
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF))
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = currentCerita.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = { /* Bookmark */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookmark),
                        contentDescription = "Bookmark",
                        tint = Color(0xFF00C2FF),
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = {
                        if (isSpeaking) {
                            tts.stop()
                            isSpeaking = false
                        } else {
                            tts.speak(currentCerita.text, TextToSpeech.QUEUE_FLUSH, null, null)
                            isSpeaking = true
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isSpeaking) R.drawable.on_audio else R.drawable.mute_audio
                        ),
                        contentDescription = "TTS",
                        tint = Color(0xFF00C2FF),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(200.dp))

            // Isi Cerita
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentCerita.text,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Page indicator diposisikan fix di bawah layar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp) // This will move the PageIndicator to the bottom
        ) {
            PageIndicatorBaca(
                currentPage = currentPage,
                onPageChange = {
                    currentPage = it
                    if (it == ceritaPages.lastIndex) {
                        showFinishDialog = true
                    }
                },
                pageCount = ceritaPages.size
            )
        }
        // Tombol "Selesai" hanya muncul di halaman terakhir
        if (currentPage == ceritaPages.lastIndex) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { showFinishDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7DE2FC)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Selesai", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Pop-Up Dialog muncul saat tombol ditekan
        if (showFinishDialog) {
            AlertDialog(
                onDismissRequest = { showFinishDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showFinishDialog = false
                            navController.navigate(Screen.Quiz.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7DE2FC)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_quiz),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Lanjut Kuis", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                },
                title = {
                    Text(
                        "Yeay Cerita Selesai!",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00475D),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Text(
                        "Sekarang, Yuk Uji Seberapa Hebat Pemahaman Ceritamu Lewat Kuis!",
                        color = Color(0xFF00475D),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.robot_kuis),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}


