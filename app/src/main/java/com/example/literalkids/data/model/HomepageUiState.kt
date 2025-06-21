package com.example.literalkids.data.model

import com.example.literalkids.R

/**
 * Merepresentasikan semua data dan kondisi yang dibutuhkan oleh HomepageUI.
 * Ini adalah satu-satunya objek state yang akan diamati oleh UI.
 */
data class HomepageUiState(
    val user: HomeUser? = null,
    val continueStories: List<Story> = emptyList(),
    val recommendedStories: List<Story> = emptyList(),
    val popularStories: List<Story> = emptyList(),
    val bannerImages: List<Int> = listOf(
        R.drawable.banner_kancil,
        R.drawable.banner_istana,
        R.drawable.banner_pohon
    ),
    val isLoading: Boolean = false,
    val error: String? = null
)