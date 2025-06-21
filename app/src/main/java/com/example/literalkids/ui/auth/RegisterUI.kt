package com.example.literalkids.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.literalkids.viewmodel.RegisterUiState
import com.example.literalkids.viewmodel.RegisterViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import androidx.navigation.compose.rememberNavController
import com.example.literalkids.data.local.TokenManager
import com.example.literalkids.data.network.ApiService
import com.example.literalkids.data.repository.AuthRepository
import com.example.literalkids.viewmodel.LoginViewModel
import retrofit2.Retrofit

@Composable
fun RegisterUI(navController: NavController, viewModel: RegisterViewModel) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val uiState by viewModel.registerUiState.collectAsState()
    val context = LocalContext.current
    val emailError by viewModel.emailError.collectAsState()

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is RegisterUiState.RegisterSuccess -> {
                Toast.makeText(context, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_LONG).show()
                // Arahkan ke halaman login setelah sukses
                navController.navigate(Screen.OnBoarding1.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
            is RegisterUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CurvedHeader()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

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

            Text("Daftar", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = viewModel::onEmailChange,
                placeholder = { Text("Email") },
                // isError akan membuat border menjadi merah jika ada error
                isError = emailError != null,
                // supportingText akan menampilkan pesan error di bawah field
                supportingText = {
                    if (emailError != null) {
                        Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth(), // Hapus .height() agar bisa memuat supportingText
                shape = RoundedCornerShape(25.dp),
                singleLine = true // Agar lebih rapi
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(25.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                placeholder = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(25.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.register() }, // <-- Perbaiki
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = uiState !is RegisterUiState.Loading // <-- Tambahkan
            ) {
                if (uiState is RegisterUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Buat Akun", color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(1f)) { Divider(color = Color.LightGray) }
                Text(" atau ", fontSize = 12.sp, color = Color.Gray)
                Box(modifier = Modifier.weight(1f)) { Divider(color = Color.LightGray) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = { /* Login dengan Google */ },
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
                    onClick = { /* Login dengan Facebook */ },
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
                Text("Sudah punya akun?", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Login",
                    color = Color(0xFF64D2FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(x = 16.dp, y = 40.dp)
                .size(40.dp)
                .background(Color(0xFF64D2FF), shape = CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .clickable {
                    navController.popBackStack()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tombol_back),
                contentDescription = "Back",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
fun RegisterUIPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val apiService = Retrofit.Builder()
        .baseUrl("https://literakids-api.up.railway.app/api/register/") // Replace with a valid base URL
        .build()
        .create(ApiService::class.java)
    val tokenManager = TokenManager(context)
    val authRepository = AuthRepository(apiService, tokenManager)
    val viewModel = RegisterViewModel(authRepository)
    RegisterUI(
        navController = navController,
        viewModel = viewModel
    )
}