package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.literalkids.data.model.HomepageUiState
import com.example.literalkids.data.repo.StoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomepageViewModel(private val repository: StoryRepository = StoryRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow(HomepageUiState(isLoading = true))
    val uiState: StateFlow<HomepageUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val stories = repository.getStories()
                val user = repository.getUser()
                val bannerImages = repository.getBannerImages()
                _uiState.value = HomepageUiState(
                    user = user,
                    continueStories = stories.filter { it.progress > 0f }.take(2),
                    recommendedStories = stories.filter { it.category == "Fabel" || it.category == "Komedi" || it.category == "Mitos" },
                    popularStories = stories.sortedByDescending { it.views.replace(",", "").replace(" Ribu Dibaca", "").toFloat() },
                    bannerImages = bannerImages,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = HomepageUiState(
                    isLoading = false,
                    error = "Gagal memuat data: ${e.message}"
                )
            }
        }
    }
}