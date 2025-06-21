package com.example.literalkids.viewmodel // atau package ui.auth Anda

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.literalkids.data.model.OnboardingModel
import com.example.literalkids.data.repository.AuthRepository
import com.example.literalkids.data.repository.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// State untuk proses submit data ke API
sealed class OnboardingSubmitState {
    object Idle : OnboardingSubmitState()
    object Loading : OnboardingSubmitState()
    object Success : OnboardingSubmitState()
    data class Error(val message: String) : OnboardingSubmitState()
}

// ViewModel sekarang butuh Repository sebagai "bahan"-nya
class OnboardingViewModel(private val repository: AuthRepository) : ViewModel() {

    // State untuk halaman aktif (pagination) -> TIDAK BERUBAH
    private val _currentPage = mutableStateOf(0)
    val currentPage: State<Int> = _currentPage

    // State untuk menampung data yang diisi pengguna -> TIDAK BERUBAH
    private val _onboardingData = mutableStateOf(OnboardingModel())
    val onboardingData: State<OnboardingModel> = _onboardingData

    // State BARU untuk proses submit ke API
    private val _submitState = mutableStateOf<OnboardingSubmitState>(OnboardingSubmitState.Idle)
    val submitState: State<OnboardingSubmitState> = _submitState

    // --- Fungsi untuk update data dari UI (Tidak berubah) ---
    fun updateChildName(name: String) { _onboardingData.value = _onboardingData.value.copy(childName = name) }
    fun updateChildUsername(username: String) { _onboardingData.value = _onboardingData.value.copy(childUsername = username) }
    fun updateParentName(name: String) { _onboardingData.value = _onboardingData.value.copy(parentName = name) }
    fun updateParentUsername(username: String) { _onboardingData.value = _onboardingData.value.copy(parentUsername = username) }
    fun updateReferralCode(code: String) { _onboardingData.value = _onboardingData.value.copy(referralCode = code) }
    fun updateSelectedPackage(index: Int) { _onboardingData.value = _onboardingData.value.copy(selectedPackageIndex = index) }
    fun nextPage() { if (_currentPage.value < 3) _currentPage.value++ }
    fun previousPage() { if (_currentPage.value > 0) _currentPage.value-- }

    // --- Fungsi utama untuk menyelesaikan onboarding (LOGIKANYA BERUBAH TOTAL) ---
    fun completeOnboarding(isSkippingSubscription: Boolean = false) {
        if (_submitState.value is OnboardingSubmitState.Loading) return

        viewModelScope.launch {
            _submitState.value = OnboardingSubmitState.Loading

            // Buat salinan data yang akan dikirim
            var dataToSend = _onboardingData.value

            // Jika pengguna melewati halaman subscription, atur index paket ke nilai khusus
            // Misalnya -1 untuk menandakan tidak ada paket yang dipilih
            if (isSkippingSubscription) {
                dataToSend = dataToSend.copy(selectedPackageIndex = -1) // atau 0 sesuai logika bisnis Anda
            }

            // 2. Panggil repository untuk mengirim data yang sudah terkumpul
            when (val result = repository.submitOnboarding(dataToSend)) {
                is AuthResult.Success -> {
                    // 3a. Jika sukses, ubah state menjadi Success
                    _submitState.value = OnboardingSubmitState.Success
                }
                is AuthResult.Failure -> {
                    // 3b. Jika gagal, ubah state menjadi Error dan bawa pesan errornya
                    _submitState.value = OnboardingSubmitState.Error(result.errorMessage)
                }
                is AuthResult.NetworkError -> {
                    // 3c. Jika gagal karena jaringan
                    _submitState.value = OnboardingSubmitState.Error("Periksa koneksi internet Anda.")
                }

            }
        }
    }
}

// Buat Factory-nya agar bisa di-inject dari MainActivity
class OnboardingViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnboardingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}