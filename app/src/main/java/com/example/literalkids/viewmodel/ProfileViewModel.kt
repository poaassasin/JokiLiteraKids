package com.example.literalkids.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    val isDarkTheme: Boolean = false // Tambah state untuk dark theme
)

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        fetchProfileData()
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            val childData = UserData(
                id = "user11",
                fullName = "Levi Annora",
                username = "leviannora",
                level = 7,
                currentXp = 90,
                maxXp = 100,
                age = 5,
                gender = "perempuan",
                schoolLevel = "TK",
                birthDate = "7/4/2025",
                avatarUrl = R.drawable.default_avatar,
                coins = 450,
                type = "child",
                ownedAvatars = listOf(R.drawable.default_avatar)
            )
            val parentData = UserData(
                id = "user12",
                fullName = "Adinda Febyola",
                username = "febydinda",
                level = 38,
                birthDate = "1995-02-20",
                avatarUrl = R.drawable.parent_avatar,
                phoneNumber = "082198765432",
                occupation = "Ibu Rumah Tangga",
                relationship = "Ibu",
                type = "parent"
            )
            val avatarOptions = listOf(
                AvatarOption(1, "Penyihir", 200, R.drawable.default_avatar),
                AvatarOption(2, "Dinosaurus", 200, R.drawable.avatar_dino),
                AvatarOption(3, "Kuda Poni", 200, R.drawable.avatar_poni),
                AvatarOption(4, "Bunga", 200, R.drawable.avatar_bunga),
                AvatarOption(5, "Katak", 200, R.drawable.avatar_katak),
                AvatarOption(6, "Ksatria", 250, R.drawable.avatar_ksatria)
            )
            _uiState.value = ProfileUiState(
                childData = childData,
                parentData = parentData,
                avatarOptions = avatarOptions,
                selectedAvatar = childData.avatarUrl
            )
        }
    }

    fun toggleDarkTheme() {
        _uiState.value = _uiState.value.copy(isDarkTheme = !_uiState.value.isDarkTheme)
    }

    fun updateAvatar(imageUrl: Int) {
        val newChildData = _uiState.value.childData.copy(avatarUrl = imageUrl)
        _uiState.value = _uiState.value.copy(
            childData = newChildData,
            selectedAvatar = imageUrl
        )
    }

    fun purchaseAvatar(imageUrl: Int, price: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)

        Handler(Looper.getMainLooper()).postDelayed({
            val currentChildData = _uiState.value.childData
            if (currentChildData.coins >= price) {
                val newOwnedAvatars = currentChildData.ownedAvatars.toMutableList()
                if (!newOwnedAvatars.contains(imageUrl)) {
                    newOwnedAvatars.add(imageUrl)
                }
                val newChildData = currentChildData.copy(
                    ownedAvatars = newOwnedAvatars,
                    coins = currentChildData.coins - price
                )
                _uiState.value = _uiState.value.copy(
                    childData = newChildData,
                    purchaseSuccess = true,
                    isLoading = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    purchaseError = "Koin tidak cukup untuk membeli avatar ini.",
                    isLoading = false
                )
            }
        }, 1000)
    }

    fun setShowOnlyOwned(show: Boolean) {
        _uiState.value = _uiState.value.copy(showOnlyOwned = show)
    }

    fun setShowPurchaseDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showPurchaseDialog = show)
    }

    fun setSelectedAvatarForPurchase(avatar: AvatarOption?) {
        _uiState.value = _uiState.value.copy(selectedAvatarForPurchase = avatar)
    }

    fun resetPurchaseStatus() {
        _uiState.value = _uiState.value.copy(
            purchaseSuccess = false,
            purchaseError = null
        )
    }

    fun updateChildProfile(
        fullName: String,
        age: Int,
        gender: String?,
        schoolLevel: String,
        birthDate: String
    ) {
        val newChildData = _uiState.value.childData.copy(
            fullName = fullName,
            age = age,
            gender = gender,
            schoolLevel = schoolLevel,
            birthDate = birthDate
        )
        _uiState.value = _uiState.value.copy(childData = newChildData)
    }

    fun updateParentProfile(
        fullName: String,
        phoneNumber: String,
        occupation: String,
        relationship: String,
        birthDate: String
    ) {
        val newParentData = _uiState.value.parentData.copy(
            fullName = fullName,
            phoneNumber = phoneNumber,
            occupation = occupation,
            relationship = relationship,
            birthDate = birthDate
        )
        _uiState.value = _uiState.value.copy(parentData = newParentData)
    }
}