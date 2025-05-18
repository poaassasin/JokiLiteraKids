package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.literalkids.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Model
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

// Repository
class StoryRepository {
    fun getStories(): List<Story> {
        return listOf(
            Story(1, "Si Kancil dan Buaya", R.drawable.kancil_buaya, 0.8f, "Fabel", "4,2 Ribu Dibaca"),
            Story(2, "Buaya dan Kerbau", R.drawable.buaya_kerbau, 0.6f, "Fabel", "2,1 Ribu Dibaca"),
            Story(3, "Kancil Mencuri Ketimun", R.drawable.kancil_ketimun, 0.0f, "Komedi", "4,2 Ribu Dibaca"),
            Story(4, "Asal Usul Danau Toba", R.drawable.danau_toba, 0.0f, "Legenda", "2,1 Ribu Dibaca"),
            Story(5, "Timun Mas dan Buto Ijo", R.drawable.timun_mas, 0.0f, "Mitos", "1,8 Ribu Dibaca"),
            Story(6, "Petualangan Lutung", R.drawable.lutung, 0.0f, "Fantasi", "6,8 Ribu Dibaca"),
            Story(7, "Si Burung Pipit Penolong", R.drawable.pipit, 0.0f, "Fabel", "9,2 Ribu Dibaca"),
            Story(8, "Putri Bambu Gunung Arjuna", R.drawable.putri_bambu, 0.0f, "Legenda", "8,9 Ribu Dibaca")
        )
    }

    fun getUser(): User {
        return User("Levi Annora", "@leviannora", 7, 0.68f)
    }

    fun getBannerImages(): List<Int> {
        return listOf(
            R.drawable.banner_kancil,
            R.drawable.banner_istana,
            R.drawable.banner_pohon
        )
    }
}

// ViewModel
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