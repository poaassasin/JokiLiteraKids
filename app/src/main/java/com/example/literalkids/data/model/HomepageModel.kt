package com.example.literalkids.data.model

import com.example.literalkids.R

data class Story(
    val id: Int,
    val title: String,
    val imageRes: Int,
    val progress: Float,
    val category: String,
    val views: String
)

data class User(
    val name: String,
    val username: String,
    val level: Int,
    val progress: Float
)

data class HomepageUiState(
    val user: User? = null,
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