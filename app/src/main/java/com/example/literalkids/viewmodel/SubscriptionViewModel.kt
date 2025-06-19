
package com.example.literalkids.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.literalkids.data.model.SubscriptionPlan
import com.example.literalkids.data.model.SubscriptionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class SubscriptionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SubscriptionUiState(isLoading = true))
    val uiState: StateFlow<SubscriptionUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val plans = getSubscriptionPlans()
                val activePlan = getActivePlan()
                val referralCode = generateReferralCode()
                _uiState.value = SubscriptionUiState(
                    plans = plans,
                    activePlan = activePlan,
                    selectedPlanId = activePlan?.id ?: 1, // Default ke Paket Ceria jika tidak ada activePlan
                    referralCode = referralCode,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = SubscriptionUiState(
                    isLoading = false,
                    error = "Gagal memuat data: ${e.message}"
                )
            }
        }
    }

    private fun getSubscriptionPlans(): List<SubscriptionPlan> {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val startDate = dateFormat.format(calendar.time)

        return listOf(
            SubscriptionPlan(
                id = 1,
                title = "Paket Ceria",
                price = "Rp25.000 Selama 1 Bulan",
                subscriptionPeriod = calculatePeriod(1, startDate) // 1 bulan
            ),
            SubscriptionPlan(
                id = 2,
                title = "Paket Hebat",
                price = "Rp65.000 Selama 3 Bulan",
                savings = "Hemat Hingga Rp10.000",
                subscriptionPeriod = calculatePeriod(3, startDate) // 3 bulan
            ),
            SubscriptionPlan(
                id = 3,
                title = "Paket Juara",
                price = "Rp180.000 Selama 12 Bulan",
                savings = "Setara 4 Bulan Gratis",
                subscriptionPeriod = calculatePeriod(12, startDate) // 12 bulan
            )
        )
    }

    private fun calculatePeriod(months: Int, startDate: String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(startDate)!!
        calendar.add(Calendar.MONTH, months)
        val endDate = dateFormat.format(calendar.time)
        return "$startDate - $endDate"
    }

    private fun getActivePlan(): SubscriptionPlan? {
        // Untuk simulasi: awalnya null (tidak ada langganan aktif)
        return null
    }

    private fun generateReferralCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..6).map { chars[Random.nextInt(chars.length)] }.joinToString("")
    }

    fun selectPlan(planId: Int) {
        _uiState.value = _uiState.value.copy(selectedPlanId = planId)
    }

    fun subscribeToPlan() {
        val selectedPlan = _uiState.value.plans.find { it.id == _uiState.value.selectedPlanId }
        if (selectedPlan != null) {
            _uiState.value = _uiState.value.copy(activePlan = selectedPlan)
        }
    }

    fun copyReferralCode(context: Context) {
        val referralCode = _uiState.value.referralCode
        val clipboard = getSystemService(context, ClipboardManager::class.java)
        val clip = ClipData.newPlainText("Referral Code", referralCode)
        clipboard?.setPrimaryClip(clip)
    }
}
