package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Model
data class SubscriptionPlan(
    val id: Int,
    val title: String,
    val price: String,
    val savings: String? = null,
    val subscriptionPeriod: String? = null
)

// UI State
data class SubscriptionUiState(
    val plans: List<SubscriptionPlan> = emptyList(),
    val activePlan: SubscriptionPlan? = null,
    val selectedPlanId: Int = 1, // Default: Paket Ceria
    val referralCode: String = "VK2Z4A",
    val isLoading: Boolean = false,
    val error: String? = null
)

// Repository
class SubscriptionRepository {
    fun getSubscriptionPlans(): List<SubscriptionPlan> {
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

    fun getActivePlan(): SubscriptionPlan? {
        // Untuk simulasi: awalnya null (tidak ada langganan aktif)
        return null
    }

    fun getReferralCode(): String {
        return "VK2Z4A"
    }
}

// ViewModel
class SubscriptionViewModel(
    private val repository: SubscriptionRepository = SubscriptionRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(SubscriptionUiState(isLoading = true))
    val uiState: StateFlow<SubscriptionUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val plans = repository.getSubscriptionPlans()
                val activePlan = repository.getActivePlan()
                val referralCode = repository.getReferralCode()
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

    fun selectPlan(planId: Int) {
        _uiState.value = _uiState.value.copy(selectedPlanId = planId)
    }

    fun subscribeToPlan() {
        val selectedPlan = _uiState.value.plans.find { it.id == _uiState.value.selectedPlanId }
        if (selectedPlan != null) {
            _uiState.value = _uiState.value.copy(activePlan = selectedPlan)
        }
    }
}