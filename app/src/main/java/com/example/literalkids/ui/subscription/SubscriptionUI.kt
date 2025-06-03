package com.example.literalkids.ui.subscription

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import com.example.literalkids.viewmodel.SubscriptionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SubscriptionUI(navController: NavHostController, viewModel: SubscriptionViewModel = viewModel()) {
    Log.d("NAV_CHECK", "Masuk ke SubscriptionUI")
    val uiState by viewModel.uiState.collectAsState()
    val gradientBackground = Brush.verticalGradient(colors = listOf(Color(0xFF7BDDFB), Color(0xFFD7A5FF)))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header dengan tombol kembali dan judul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBackground)
                .padding(16.dp)
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
                        .clickable { navController.navigate(Screen.Profile.route) }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Langganan",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
            Column(modifier = Modifier.padding(16.dp)) {
                // Paket Aktif
                uiState.activePlan?.let { plan ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(colors = listOf(Color(0xFF7BDDFB), Color(0xFFD7A5FF))),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.sc_robot),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Yay kamu sedang berlangganan!", color = Color.White)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(plan.title, color = Color.White, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Periode Berlangganan: ${plan.subscriptionPeriod}",
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Kode Referral
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(colors = listOf(Color(0xFF5AD8FF), Color(0xFFDE99FF))),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Kode Referral",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = uiState.referralCode,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterVertically),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.copy),
                                contentDescription = "Copy",
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gunakan kode referral di atas dan dapatkan berbagai hadiah menarik setiap temanmu bergabung dengan Literalkids!",
                            color = Color.White,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Kartu Langganan
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.plans) { plan ->
                        SubscriptionCard(
                            title = plan.title,
                            price = plan.price,
                            selected = plan.id == uiState.selectedPlanId,
                            savings = plan.savings,
                            onClick = { viewModel.selectPlan(plan.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol Dinamis
                Button(
                    onClick = {
                        if (uiState.activePlan == null) {
                            viewModel.subscribeToPlan()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.activePlan == null) Color(0xFF5AD8FF) else Color.LightGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = uiState.activePlan == null,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (uiState.activePlan == null) "Pilih Langganan" else "Sedang Berlangganan",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SubscriptionCard(
    title: String,
    price: String,
    selected: Boolean,
    savings: String?,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFF00AEEF) else Color.LightGray
    val backgroundColor = Color.White
    val checkIcon = if (selected) R.drawable.check_blue else null
    val blue900 = Color(0xFF003049)

    Box(
        modifier = Modifier
            .width(180.dp)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Box {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Judul
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = blue900
                )

                // Harga + durasi
                Row {
                    Text(
                        text = price.substringBefore("Selama").trim(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = blue900
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = price.substringAfter("Rp").substringAfter(" ").trim(),
                        fontSize = 12.sp,
                        color = blue900,
                        modifier = Modifier.align(Alignment.Bottom)
                    )
                }

                // Savings
                if (savings != null) {
                    Text(
                        text = savings,
                        fontSize = 10.sp,
                        color = blue900,
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

                // Fitur
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeatureItem("Akses Semua Cerita")
                    FeatureItem("Kuis Interaktif")
                    FeatureItem("Statistik & Laporan Untuk Orang Tua")
                }
            }

            // Checklist biru
            if (checkIcon != null) {
                Image(
                    painter = painterResource(id = checkIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
fun FeatureItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.checklist),
            contentDescription = null,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF003049)
        )
    }
}