package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.literalkids.data.model.LoginRequest
import com.example.literalkids.data.repository.AuthRepository
import com.example.literalkids.data.repository.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Definisikan semua kemungkinan kondisi UI untuk halaman Login
sealed class LoginUiState {
    object Idle : LoginUiState()      // Kondisi awal
    object Loading : LoginUiState()   // Saat tombol login ditekan
    object LoginSuccess : LoginUiState() // Saat API merespon sukses
    data class Error(val message: String) : LoginUiState() // Saat API merespon gagal
}

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // State untuk menampung input email dari UI
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    // State untuk menampung input password dari UI
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // State untuk merepresentasikan kondisi UI (loading, success, error)
    // Inilah yang akan diamati oleh LoginUI.kt
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    // Fungsi ini dipanggil oleh TextField email di UI setiap kali ada perubahan
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    // Fungsi ini dipanggil oleh TextField password di UI setiap kali ada perubahan
    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    // Fungsi utama yang dipanggil saat tombol Login ditekan
    fun login() {
        // Gunakan viewModelScope, coroutine yang otomatis berhenti jika ViewModel hancur
        viewModelScope.launch {
            // 1. Ubah state menjadi Loading, UI akan menampilkan progress indicator
            _loginUiState.value = LoginUiState.Loading

            val request = LoginRequest(email.value.trim(), password.value)

            // 2. Panggil fungsi login dari repository
            when (val result = authRepository.loginUser(request)) {
                is AuthResult.Success -> {
                    // 3a. Jika repository bilang sukses, ubah state menjadi Success
                    _loginUiState.value = LoginUiState.LoginSuccess
                }
                is AuthResult.Failure -> {
                    // 3b. Jika gagal, ubah state menjadi Error dengan pesan dari repository
                    _loginUiState.value = LoginUiState.Error(result.errorMessage)
                }
                is AuthResult.NetworkError -> {
                    // 3c. Jika gagal karena jaringan
                    _loginUiState.value = LoginUiState.Error("Periksa koneksi internet Anda.")
                }
            }
        }
    }
}

// Factory ini seperti "resep" untuk membuat LoginViewModel.
// Kita butuh ini karena LoginViewModel punya "bahan" (dependency) yaitu AuthRepository.
class LoginViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}