package com.example.literalkids.viewmodel

import com.example.literalkids.R
import kotlinx.coroutines.delay

data class LeaderboardUser(
    val id: String,
    val fullName: String,
    val username: String,
    val level: Int,
    val avatarUrl: Int,
    val isCurrentUser: Boolean = false
)

sealed class LeaderboardState {
    object Loading : LeaderboardState()
    data class Success(val users: List<LeaderboardUser>) : LeaderboardState()
    data class Error(val message: String) : LeaderboardState()
}

suspend fun getLeaderboardUsers(): List<LeaderboardUser> {
    delay(1000)

    val mockUsers = listOf(
        LeaderboardUser("user1", "Ayu Dewi", "ayudewi", 99, R.drawable.avatar_bunga),
        LeaderboardUser("user2", "Fahri Hidayat", "fahridayat", 90, R.drawable.avatar_dino),
        LeaderboardUser("user3", "Lala Rumi", "lalamimi", 44, R.drawable.avatar_ksatria),
        LeaderboardUser("user4", "Salsabilla Putri", "salsabil", 41, R.drawable.avatar_poni),
        LeaderboardUser("user5", "Ahmad Farhan", "ahmadfar", 19, R.drawable.parent_avatar),
        LeaderboardUser("user6", "Rafa Elvano", "rafaelvano", 37, R.drawable.avatar_bunga),
        LeaderboardUser("user7", "Anisa Lestari", "anisalestari", 21, R.drawable.avatar_ksatria),
        LeaderboardUser("user8", "Budi Santoso", "budisantoso", 10, R.drawable.parent_avatar),
        LeaderboardUser("user9", "Kalea Syakira", "inisyakira", 29, R.drawable.avatar_katak),
        LeaderboardUser("user10", "Fayra Alesha", "feyysha", 28, R.drawable.avatar_dino),
        LeaderboardUser("user11", "Levi Annora", "leviannora", 7, R.drawable.default_avatar, true),
        LeaderboardUser("user12", "Adinda Febyola", "febydinda", 38, R.drawable.parent_avatar)
    )

    return mockUsers.sortedByDescending { it.level }
}