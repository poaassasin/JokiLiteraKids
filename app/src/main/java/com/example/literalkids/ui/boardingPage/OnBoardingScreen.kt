package com.example.literalkids.ui.boardingPage

import android.util.Log
import androidx.compose.material3.MaterialTheme
import android.widget.Toast
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import com.google.accompanist.pager.*
import androidx.compose.runtime.rememberCoroutineScope
import com.example.literalkids.data.local.TokenManager
import com.example.literalkids.data.network.ApiService
import com.example.literalkids.data.repository.AuthRepository
import com.example.literalkids.viewmodel.OnboardingSubmitState
import com.example.literalkids.viewmodel.OnboardingViewModel
import com.example.literalkids.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(navController: NavController, viewModel: OnboardingViewModel) {
    val submitState by viewModel.submitState
    val currentPage by viewModel.currentPage
    val context = LocalContext.current
    val onboardingState by viewModel.onboardingData
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    val childNameError by viewModel.childNameError
    val childUsernameError by viewModel.childUsernameError
    val parentNameError by viewModel.parentNameError
    val parentUsernameError by viewModel.parentUsernameError

    // Animasi slide berdasarkan currentPage
    val animatedPage by animateIntAsState(targetValue = currentPage)

    LaunchedEffect(submitState) {
        when(val state = submitState) {
            is OnboardingSubmitState.Success -> {
                Toast.makeText(context, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
                // Navigasi ke Homepage jika submit API berhasil
                navController.navigate(Screen.Homepage.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
            is OnboardingSubmitState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> { /* Tidak melakukan apa-apa untuk Idle dan Loading */ }
        }
    }

    // Sinkronkan pagerState dengan ViewModel
    LaunchedEffect(viewModel.currentPage.value) {
        pagerState.animateScrollToPage(viewModel.currentPage.value)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.header_background),
            contentDescription = "Header Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (currentPage > 0) {
                            viewModel.previousPage()
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tombol_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Kembali",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(160.dp))

            // Dot indicator
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(4) { index ->
                    if (index != 0) Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = if (index == currentPage) {
                            Modifier
                                .size(20.dp, 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFF64D2FF))
                        } else {
                            Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0F7FF))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Konten halaman menggunakan HorizontalPager
            HorizontalPager(
                count = 4,
                state = pagerState,
                modifier = Modifier.weight(1f),
                userScrollEnabled = false // Nonaktifkan swipe
            ) { page ->
                // Animasi slide
                val translationX = if (page == animatedPage) {
                    0f
                } else if (page < animatedPage) {
                    -1000f // Geser ke kiri jika halaman sebelumnya
                } else {
                    1000f // Geser ke kanan jika halaman berikutnya
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { this.translationX = translationX },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (page) {
                        0 -> {
                            Text(
                                text = "Siapa Nama Si Kecil?",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A5470)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Kamu selalu dapat mengubahnya nanti",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFB0B0B0)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                value = onboardingState.childName,
                                onValueChange = { viewModel.updateChildName(it) },
                                placeholder = {
                                    Text(
                                        "Masukkan nama Si Kecil di sini",
                                        color = Color(0xFFB0B0B0),
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(25.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = Color(0xFF64D2FF)
                                ),
                                isError = childNameError != null,
                                supportingText = {
                                    if (childNameError != null) {
                                        Text(text = childNameError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = onboardingState.childUsername,
                                onValueChange = { viewModel.updateChildUsername(it) },
                                placeholder = {
                                    Text(
                                        "Masukkan nama pengguna Si Kecil di sini",
                                        color = Color(0xFFB0B0B0),
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(25.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = Color(0xFF64D2FF)
                                ),
                                singleLine = true,
                                isError = childUsernameError != null,
                                supportingText = {
                                    if (childUsernameError != null) {
                                        Text(text = childUsernameError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        1 -> {
                            Text(
                                text = "Siapa Nama Orang Tua?",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A5470)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Kamu selalu dapat mengubahnya nanti",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFB0B0B0)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                value = onboardingState.parentName,
                                onValueChange = { viewModel.updateParentName(it) },
                                placeholder = {
                                    Text(
                                        "Masukkan nama orang tua/pendamping di sini",
                                        color = Color(0xFFB0B0B0),
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(25.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = Color(0xFF64D2FF)
                                ),
                                singleLine = true,
                                isError = parentNameError != null,
                                supportingText = {
                                    if (parentNameError != null) {
                                        Text(text = parentNameError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = onboardingState.parentUsername,
                                onValueChange = { viewModel.updateParentUsername(it) },
                                placeholder = {
                                    Text(
                                        "Buat nama pengguna orang tua/pendamping",
                                        color = Color(0xFFB0B0B0),
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(25.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = Color(0xFF64D2FF)
                                ),
                                singleLine = true,
                                isError = parentUsernameError != null,
                                supportingText = {
                                    if (parentUsernameError != null) {
                                        Text(text = parentUsernameError!!, color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        2 -> {
                            Text(
                                text = "Kode Referral",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A5470)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Masukkan Kode Referral & Nikmati Keuntungannya!",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFB0B0B0)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            OutlinedTextField(
                                value = onboardingState.referralCode,
                                onValueChange = { viewModel.updateReferralCode(it) },
                                placeholder = {
                                    Text(
                                        "Masukkan kode referral teman di sini",
                                        color = Color(0xFFB0B0B0),
                                        fontSize = 14.sp
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp),
                                shape = RoundedCornerShape(25.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = Color(0xFF64D2FF)
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        3 -> {
                            Text(
                                text = "Akses Cerita Lebih Banyak",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0A5470)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Column {
                                Text(
                                    text = "Yuk Gunakan Fitur Premium untuk",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFB0B0B0)
                                )
                                Text(
                                    text = "Petualangan yang Lebih Menarik.",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFB0B0B0)
                                )
                            }
                            Spacer(modifier = Modifier.height(28.dp))

                            // Paket dalam Row
                            val packages = listOf(
                                Triple("Paket Ceria", "Rp25.000", "Langganan Selama 1 Bulan"),
                                Triple("Paket Hebat", "Rp65.000", "Langganan Selama 3 Bulan"),
                                Triple("Paket Juara", "Rp120.000", "Langganan Selama 12 Bulan")
                            )
                            val originalPrices = listOf("", "Rp75.000", "Rp300.000")

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                packages.forEachIndexed { index, pkg ->
                                    val isSelected = index == onboardingState.selectedPackageIndex
                                    val scale = if (isSelected) 1f else 0.8f
                                    val alpha = if (isSelected) 1f else 0.6f

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { viewModel.updateSelectedPackage(index) },
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        if (index == 1) {
                                            Box(
                                                modifier = Modifier
                                                    .offset(y = (-8).dp)
                                                    .zIndex(1f)
                                                    .background(Color(0xFF5AD8FF), RoundedCornerShape(8.dp))
                                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = "Terlaris",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                            }
                                        }

                                        Box(
                                            modifier = Modifier
                                                .height(220.dp)
                                                .graphicsLayer {
                                                    scaleX = scale
                                                    scaleY = scale
                                                    this.alpha = alpha
                                                }
                                                .clip(RoundedCornerShape(16.dp))
                                                .border(
                                                    width = 1.dp,
                                                    color = if (isSelected) Color(0xFF5AD8FF) else Color(0xFFD5D5D5),
                                                    shape = RoundedCornerShape(16.dp)
                                                )
                                                .background(Color.White)
                                                .padding(12.dp)
                                                .padding(top = 4.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                    Text(
                                                        text = pkg.first,
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = Color(0xFF0A5470)
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))

                                                    if (originalPrices[index].isNotEmpty()) {
                                                        Text(
                                                            text = originalPrices[index],
                                                            fontSize = 10.sp,
                                                            color = Color(0xFFB0B0B0),
                                                            style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                                                        )
                                                    }

                                                    Text(
                                                        text = pkg.second,
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF0A5470)
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))

                                                    Text(
                                                        text = pkg.third,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = Color(0xFF0A5470),
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.fillMaxWidth()
                                                    )
                                                }

                                                if (isSelected) {
                                                    Button(
                                                        onClick = { viewModel.completeOnboarding(isSkippingSubscription = false) },
                                                        shape = RoundedCornerShape(50),
                                                        colors = ButtonDefaults.buttonColors(
                                                            containerColor = Color(0xFF64D2FF),
                                                            contentColor = Color.White
                                                        ),
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(40.dp),
                                                        enabled = submitState !is OnboardingSubmitState.Loading
                                                    ) {
                                                        if (submitState is OnboardingSubmitState.Loading) {
                                                            CircularProgressIndicator(
                                                                modifier = Modifier.size(22.dp),
                                                                color = Color.White,
                                                                strokeWidth = 2.dp
                                                            )
                                                        } else {
                                                            Text(
                                                                text = "Beli",
                                                                fontWeight = FontWeight.Bold,
                                                                fontSize = 14.sp
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    Spacer(modifier = Modifier.height(40.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    val isButtonEnabled = viewModel.isNextButtonEnabled()

                    if (page < 3) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    viewModel.nextPage()
                                    pagerState.animateScrollToPage(page + 1)
                                }
                            },
                            enabled = isButtonEnabled,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isButtonEnabled) Color(0xFF64D2FF) else Color(0xFFD1D1D1),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Selanjutnya", fontSize = 16.sp)
                        }
                    }

                    if (page >= 2) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Lewati",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF5AD8FF),
                            modifier = Modifier.clickable {
                                viewModel.completeOnboarding(isSkippingSubscription = true)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val apiService = Retrofit.Builder()
        .baseUrl("https://literakids-api.up.railway.app/api/onboarding/") // Replace with a valid base URL
        .build()
        .create(ApiService::class.java)
    val tokenManager = TokenManager(context)
    val authRepository = AuthRepository(apiService, tokenManager)
    val viewModel = OnboardingViewModel(authRepository)
    OnboardingScreen(navController = navController, viewModel = viewModel)
}
