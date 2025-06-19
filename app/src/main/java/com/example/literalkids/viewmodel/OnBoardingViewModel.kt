package com.example.literalkids.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.literalkids.data.model.OnboardingModel

class OnboardingViewModel : ViewModel() {
    // State untuk halaman aktif
    private val _currentPage = mutableStateOf(0)
    val currentPage: State<Int> = _currentPage

    // State untuk data onboarding
    private val _onboardingData = mutableStateOf(OnboardingModel())
    val onboardingData: State<OnboardingModel> = _onboardingData

    // Fungsi untuk mengubah input
    fun updateChildName(name: String) {
        _onboardingData.value = _onboardingData.value.copy(childName = name)
    }

    fun updateChildUsername(username: String) {
        _onboardingData.value = _onboardingData.value.copy(childUsername = username)
    }

    fun updateParentName(name: String) {
        _onboardingData.value = _onboardingData.value.copy(parentName = name)
    }

    fun updateParentUsername(username: String) {
        _onboardingData.value = _onboardingData.value.copy(parentUsername = username)
    }

    fun updateReferralCode(code: String) {
        _onboardingData.value = _onboardingData.value.copy(referralCode = code)
    }

    // Fungsi untuk mengubah paket yang dipilih
    fun updateSelectedPackage(index: Int) {
        _onboardingData.value = _onboardingData.value.copy(selectedPackageIndex = index)
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
            0 -> _onboardingData.value.childName.isNotBlank()
            1 -> _onboardingData.value.childUsername.isNotBlank()
            2 -> _onboardingData.value.parentName.isNotBlank()
            3 -> _onboardingData.value.parentUsername.isNotBlank()
            4 -> _onboardingData.value.referralCode.isNotBlank()
            5 -> true // Halaman langganan tidak perlu input
            else -> false
        }
    }

    // Logika penyelesaian onboarding
    fun completeOnboarding(context: Context) {
        val sharedPref = context.getSharedPreferences("OnboardingPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("childName", _onboardingData.value.childName)
            putString("childUsername", _onboardingData.value.childUsername)
            putString("parentName", _onboardingData.value.parentName)
            putString("parentUsername", _onboardingData.value.parentUsername)
            putString("referralCode", _onboardingData.value.referralCode)
            putInt("selectedPackageIndex", _onboardingData.value.selectedPackageIndex)
            putBoolean("onboardingCompleted", true)
            apply()
        }
        Log.d("Onboarding", "Onboarding selesai: " +
                "ChildName=${_onboardingData.value.childName}, " +
                "ChildUsername=${_onboardingData.value.childUsername}, " +
                "ParentName=${_onboardingData.value.parentName}, " +
                "ParentUsername=${_onboardingData.value.parentUsername}, " +
                "ReferralCode=${_onboardingData.value.referralCode}, " +
                "SelectedPackage=${_onboardingData.value.selectedPackageIndex}")
    }
}