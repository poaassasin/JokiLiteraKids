package com.example.literalkids.ui.auth

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.R
import com.example.literalkids.data.local.TokenManager
import com.example.literalkids.data.network.ApiService
import com.example.literalkids.data.repository.AuthRepository
import com.example.literalkids.navigation.Screen
import com.example.literalkids.viewmodel.LoginUiState
import com.example.literalkids.viewmodel.LoginViewModel
import retrofit2.Retrofit

@Composable
fun CurvedHeader() {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp + statusBarHeight)
            .padding(top = statusBarHeight)
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, height * 0.85f)
            cubicTo(
                width * 0.20f, height * 0.10f,
                width * 0.75f, height * 0.04f,
                width, height * 0.3f
            )
                    lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        drawPath(path = path, color = Color(0xFF64D2FF))
    }
}

// 1. Pastikan signature fungsi menerima ViewModel yang sudah jadi
@Composable
fun LoginUI(
    navController: NavController,
    viewModel: LoginViewModel
) {
    // 2. Ambil semua state yang dibutuhkan dari ViewModel
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val uiState by viewModel.loginUiState.collectAsState()
    val context = LocalContext.current

    // 3. Gunakan LaunchedEffect untuk menangani event sekali jalan (navigasi/toast)
    // Blok ini akan berjalan setiap kali `uiState` berubah
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.LoginSuccess -> {
                Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                // Navigasi ke homepage dan hapus semua halaman sebelumnya dari tumpukan
                navController.navigate(Screen.Homepage.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
            is LoginUiState.Error -> {
                // Tampilkan pesan error spesifik dari server
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {
                // Tidak melakukan apa-apa untuk state Idle dan Loading
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .zIndex(0f)
    ) {
        CurvedHeader()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()) // Tambahkan scroll agar tidak overflow di layar kecil
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            // ... (Bagian Image dan Text "Log In" tidak berubah)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = "Profile Placeholder"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Log In", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = viewModel::onEmailChange, // Hubungkan ke fungsi ViewModel
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(), // Hapus .height() agar dinamis
                shape = RoundedCornerShape(25.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = viewModel::onPasswordChange, // Hubungkan ke fungsi ViewModel
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(), // Hapus .height() agar dinamis
                shape = RoundedCornerShape(25.dp)
            )

            // ... (Bagian "Lupa Password?" tidak berubah)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Lupa password?", color = Color.Gray, fontSize = 12.sp)
            }


            Spacer(modifier = Modifier.height(24.dp))

            // 4. Modifikasi Tombol Login
            Button(
                onClick = { viewModel.login() }, // Hanya panggil fungsi login
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64D2FF)),
                enabled = uiState !is LoginUiState.Loading // Tombol nonaktif saat loading
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Login", color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.LightGray)
                }
                Text(" atau ", fontSize = 12.sp, color = Color.Gray)
                Box(modifier = Modifier.weight(1f)) {
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { /* TODO: Google Login */ },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 150.dp, height = 50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Google")
                }

                OutlinedButton(
                    onClick = { /* TODO: Facebook Login */ },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 150.dp, height = 50.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_logo),
                        contentDescription = "Facebook Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Facebook")
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Belum punya akun?", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Daftar",
                    color = Color(0xFF64D2FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginUIPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val apiService = Retrofit.Builder()
        .baseUrl("https://literakids-api.up.railway.app/api/login/") // Replace with a valid base URL
        .build()
        .create(ApiService::class.java)
    val tokenManager = TokenManager(context)
    val authRepository = AuthRepository(apiService, tokenManager)
    val viewModel = LoginViewModel(authRepository)
    LoginUI(
        navController = navController,
        viewModel = viewModel
    )
}