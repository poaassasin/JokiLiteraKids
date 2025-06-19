package com.example.literalkids.data.model

import com.example.literalkids.R

data class UserData(
    val id: String = "",
    val fullName: String = "",
    val username: String = "",
    val level: Int = 1,
    val currentXp: Int = 0,
    val maxXp: Int = 100,
    val age: Int = 0,
    val gender: String? = null,
    val schoolLevel: String = "",
    val birthDate: String = "",
    val avatarUrl: Int = 0,
    val coins: Int = 0,
    val phoneNumber: String = "",
    val occupation: String = "",
    val relationship: String = "",
    val ownedAvatars: List<Int> = emptyList(),
    val type: String = "",
    val isLoading: Boolean = false
)

data class AvatarOption(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: Int
)

data class ProfileUiState(
    val childData: UserData = UserData(),
    val parentData: UserData = UserData(),
    val avatarOptions: List<AvatarOption> = emptyList(),
    val selectedAvatar: Int = R.drawable.default_avatar,
    val showOnlyOwned: Boolean = false,
    val showPurchaseDialog: Boolean = false,
    val selectedAvatarForPurchase: AvatarOption? = null,
    val purchaseSuccess: Boolean = false,
    val purchaseError: String? = null,
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false
)