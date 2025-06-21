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

    private val _childNameError = mutableStateOf<String?>(null)
    val childNameError: State<String?> = _childNameError

    private val _childUsernameError = mutableStateOf<String?>(null)
    val childUsernameError: State<String?> = _childUsernameError

    private val _parentNameError = mutableStateOf<String?>(null)
    val parentNameError: State<String?> = _parentNameError

    private val _parentUsernameError = mutableStateOf<String?>(null)
    val parentUsernameError: State<String?> = _parentUsernameError

    // --- Fungsi untuk update data dari UI (Tidak berubah) ---
    fun updateChildName(name: String) {
        _onboardingData.value = _onboardingData.value.copy(childName = name)
        if (name.trim().length < 3 && name.isNotEmpty()) {
            _childNameError.value = "Nama minimal 3 karakter"
        } else {
            _childNameError.value = null // Hapus error jika sudah valid
        }
    }
    fun updateChildUsername(username: String) {
        _onboardingData.value = _onboardingData.value.copy(childUsername = username)
        if (username.trim().length < 3 && username.isNotEmpty()) {
            _childUsernameError.value = "Username minimal 3 karakter"
        } else {
            _childUsernameError.value = null
        }
    }
    fun updateParentName(name: String) {
        _onboardingData.value = _onboardingData.value.copy(parentName = name)
        if (name.trim().length < 3 && name.isNotEmpty()) {
            _parentNameError.value = "Nama minimal 3 karakter"
        } else {
            _parentNameError.value = null
        }
    }
    fun updateParentUsername(username: String) {
        _onboardingData.value = _onboardingData.value.copy(parentUsername = username)
        if (username.trim().length < 3 && username.isNotEmpty()) {
            _parentUsernameError.value = "Username minimal 3 karakter"
        } else {
            _parentUsernameError.value = null
        }
    }
    fun updateReferralCode(code: String) { _onboardingData.value = _onboardingData.value.copy(referralCode = code) }
    fun updateSelectedPackage(index: Int) { _onboardingData.value = _onboardingData.value.copy(selectedPackageIndex = index) }
    fun nextPage() { if (_currentPage.value < 3) _currentPage.value++ }
    fun previousPage() { if (_currentPage.value > 0) _currentPage.value-- }

    fun isNextButtonEnabled(): Boolean {
        // Tombol hanya aktif jika input tidak kosong DAN tidak ada error validasi
        return when (currentPage.value) {
            0 -> onboardingData.value.childName.isNotBlank() && childNameError.value == null &&
                    onboardingData.value.childUsername.isNotBlank() && childUsernameError.value == null
            1 -> onboardingData.value.parentName.isNotBlank() && parentNameError.value == null &&
                    onboardingData.value.parentUsername.isNotBlank() && parentUsernameError.value == null
            // Halaman referral dan paket tidak kita validasi di sini
            2 -> true
            3 -> true
            else -> false
        }
    }

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