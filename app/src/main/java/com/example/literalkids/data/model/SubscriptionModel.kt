package com.example.literalkids.data.model


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
    val referralCode: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)