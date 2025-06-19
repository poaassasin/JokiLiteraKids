package com.example.literalkids.data.model

data class OnboardingModel(
    val childName: String = "",
    val childUsername: String = "",
    val parentName: String = "",
    val parentUsername: String = "",
    val referralCode: String = "",
    val selectedPackageIndex: Int = 1 // Default: Paket Hebat (index 1)
)