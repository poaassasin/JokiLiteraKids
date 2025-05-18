package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.literalkids.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Model
data class StoryItem(
    val title: String,
    val genre: String,
    val description: String,
    val imageRes: Int,
    val reads: String,
    val isFavorite: Boolean = false
)

data class GenreSearchUiState(
    val genres: List<String> = emptyList(),
    val selectedGenre: String = "",
    val stories: List<StoryItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Repository
class GenreSearchRepository {
    fun getGenres(): List<String> {
        return listOf(
            "DONGENG", "SAINS", "FABEL", "PETUALANGAN",
            "KOMEDI", "LEGENDA", "MITOS", "SLICE OF LIFE"
        )
    }

    fun getStories(): List<StoryItem> {
        return listOf(
            StoryItem("Ayam Jago Yang Sombong", "Komedi", "Seekor ayam jago merasa paling hebat di kandang...", R.drawable.ayam_jago, "3,8 Ribu"),
            StoryItem("Putri Embun Dan Pelangi", "Komedi", "Putri embun tinggal di langit dan hanya muncul saat pagi...", R.drawable.putri_embun, "4,1 Ribu"),
            StoryItem("Si Kura-Kura Pemalas", "Komedi", "Kura-kura lebih suka tidur daripada bekerja...", R.drawable.kura, "7,2 Ribu"),
            StoryItem("Naga Penjaga Gunung", "Komedi", "Seekor naga besar tinggal di puncak gunung...", R.drawable.naga, "6,5 Ribu"),
            StoryItem("Petualangan Tikus Dan Gajah", "Komedi", "Tikus kecil membantu gajah besar dari jebakan...", R.drawable.tikus_gajah, "5,5 Ribu")
        )
    }
}

// ViewModel
class GenreSearchViewModel(
    private val repository: GenreSearchRepository = GenreSearchRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenreSearchUiState(isLoading = true))
    val uiState: StateFlow<GenreSearchUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val genres = repository.getGenres()
                val stories = repository.getStories()
                _uiState.value = GenreSearchUiState(
                    genres = genres,
                    selectedGenre = genres.first(), // Default: DONGENG
                    stories = stories,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = GenreSearchUiState(
                    isLoading = false,
                    error = "Gagal memuat data: ${e.message}"
                )
            }
        }
    }

    fun selectGenre(genre: String) {
        _uiState.value = _uiState.value.copy(selectedGenre = genre)
    }
}