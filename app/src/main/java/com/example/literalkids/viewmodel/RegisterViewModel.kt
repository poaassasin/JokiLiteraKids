package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.literalkids.data.model.RegisterRequest
import com.example.literalkids.data.repository.AuthRepository
import com.example.literalkids.data.repository.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Patterns

// Kita bisa pakai ulang UI state dari Login, atau buat yang baru agar lebih jelas
sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object RegisterSuccess : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _registerUiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        // Lakukan validasi setiap kali user mengetik
        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches() && newEmail.isNotEmpty()) {
            _emailError.value = "Format email tidak valid"
        } else {
            _emailError.value = null // Hapus pesan error jika sudah valid
        }
    }
    fun onPasswordChange(newPassword: String) { _password.value = newPassword }
    fun onConfirmPasswordChange(newConfirm: String) { _confirmPassword.value = newConfirm }

    fun register() {

        if (_emailError.value != null || email.value.isEmpty()) {
            // Jika email masih tidak valid saat tombol ditekan
            _registerUiState.value = RegisterUiState.Error("Silakan perbaiki format email Anda.")
            return
        }

        viewModelScope.launch {
            _registerUiState.value = RegisterUiState.Loading
            val request = RegisterRequest(
                email = email.value.trim(),
                password = password.value,
                confirmpassword = confirmPassword.value
            )

            when (val result = authRepository.registerUser(request)) {
                is AuthResult.Success -> _registerUiState.value = RegisterUiState.RegisterSuccess
                is AuthResult.Failure -> _registerUiState.value = RegisterUiState.Error(result.errorMessage)
                is AuthResult.NetworkError -> _registerUiState.value = RegisterUiState.Error("Periksa koneksi internet Anda.")
            }
        }
    }
}

// Buat juga Factory-nya
class RegisterViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}