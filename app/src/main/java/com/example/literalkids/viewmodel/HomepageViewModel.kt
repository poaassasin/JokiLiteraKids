package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.literalkids.R
import com.example.literalkids.data.model.HomepageUiState
import com.example.literalkids.data.repository.DataResult
import com.example.literalkids.data.repository.StoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomepageViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomepageUiState(isLoading = true))
    val uiState: StateFlow<HomepageUiState> = _uiState.asStateFlow()

    init {
        fetchHomepageData()
    }

    fun fetchHomepageData() {
        // Set state menjadi loading saat mulai mengambil data
        _uiState.value = HomepageUiState(isLoading = true)

        viewModelScope.launch {
            val userId = 2L

            // Panggil repository untuk mengambil data pengguna
            when (val result = storyRepository.getHomepageUser(userId)) {
                is DataResult.Success -> {
                    val localStories = storyRepository.getStories()

                    // Jika sukses, update state dengan data pengguna dari API
                    _uiState.value = HomepageUiState(
                        isLoading = false,
                        user = result.data,
                        continueStories = localStories.filter { it.progress > 0f }.take(2),
                        recommendedStories = localStories.filter { it.category == "Fabel" || it.category == "Komedi" },
                        popularStories = localStories.sortedByDescending {
                            // Membersihkan string sebelum konversi ke float
                            it.views.replace(",", ".").replace(" Ribu Dibaca", "").toFloatOrNull() ?: 0f
                        },

                        // Banner kita buat statis untuk sementara
                        bannerImages = listOf(
                            R.drawable.banner_kancil,
                            R.drawable.banner_istana,
                            R.drawable.banner_pohon
                        )
                    )
                }
                is DataResult.Failure -> {
                    // Jika gagal, update state dengan pesan error dari API
                    _uiState.value = HomepageUiState(isLoading = false, error = result.errorMessage)
                }
                is DataResult.NetworkError -> {
                    // Jika gagal karena masalah jaringan
                    _uiState.value = HomepageUiState(isLoading = false, error = "Periksa koneksi internet Anda.")
                }
            }
            // Tambahkan pemanggilan untuk data cerita di sini nanti
        }
    }
}

class HomepageViewModelFactory(private val storyRepository: StoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomepageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomepageViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}