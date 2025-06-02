package com.example.literalkids.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    // State untuk halaman aktif
    private val _currentPage = mutableStateOf(0)
    val currentPage: State<Int> = _currentPage

    // State untuk input
    private val _childName = mutableStateOf("")
    val childName: State<String> = _childName

    private val _childUsername = mutableStateOf("")
    val childUsername: State<String> = _childUsername

    private val _parentName = mutableStateOf("")
    val parentName: State<String> = _parentName

    private val _parentUsername = mutableStateOf("")
    val parentUsername: State<String> = _parentUsername

    private val _referralCode = mutableStateOf("")
    val referralCode: State<String> = _referralCode

    // State untuk paket yang dipilih di halaman 6
    private val _selectedPackageIndex = mutableStateOf(1) // Default: Paket Hebat (index 1)
    val selectedPackageIndex: State<Int> = _selectedPackageIndex

    // Fungsi untuk mengubah input
    fun updateChildName(name: String) {
        _childName.value = name
    }

    fun updateChildUsername(username: String) {
        _childUsername.value = username
    }

    fun updateParentName(name: String) {
        _parentName.value = name
    }

    fun updateParentUsername(username: String) {
        _parentUsername.value = username
    }

    fun updateReferralCode(code: String) {
        _referralCode.value = code
    }

    // Fungsi untuk mengubah paket yang dipilih
    fun updateSelectedPackage(index: Int) {
        _selectedPackageIndex.value = index
        Log.d("Onboarding", "Paket dipilih: $index")
    }

    // Fungsi untuk navigasi halaman
    fun nextPage(): Boolean {
        return if (_currentPage.value < 5) {
            _currentPage.value += 1
            Log.d("Onboarding", "Pindah ke halaman ${_currentPage.value}")
            true
        } else {
            false
        }
    }

    fun previousPage() {
        if (_currentPage.value > 0) {
            _currentPage.value -= 1
            Log.d("Onboarding", "Kembali ke halaman ${_currentPage.value}")
        }
    }

    // Validasi input untuk tombol "Selanjutnya"
    fun isNextButtonEnabled(): Boolean {
        return when (_currentPage.value) {
            0 -> _childName.value.isNotBlank()
            1 -> _childUsername.value.isNotBlank()
            2 -> _parentName.value.isNotBlank()
            3 -> _parentUsername.value.isNotBlank()
            4 -> true // Kode referral opsional
            5 -> true // Halaman langganan tidak perlu input
            else -> false
        }
    }

    // Logika penyelesaian onboarding
    fun completeOnboarding(context: Context) {
        val sharedPref = context.getSharedPreferences("OnboardingPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("childName", _childName.value)
            putString("childUsername", _childUsername.value)
            putString("parentName", _parentName.value)
            putString("parentUsername", _parentUsername.value)
            putString("referralCode", _referralCode.value)
            putInt("selectedPackageIndex", _selectedPackageIndex.value)
            putBoolean("onboardingCompleted", true)
            apply()
        }
        Log.d("Onboarding", "Onboarding selesai: " +
                "ChildName=${_childName.value}, " +
                "ChildUsername=${_childUsername.value}, " +
                "ParentName=${_parentName.value}, " +
                "ParentUsername=${_parentUsername.value}, " +
                "ReferralCode=${_referralCode.value}, " +
                "SelectedPackage=${_selectedPackageIndex.value}")
    }
}