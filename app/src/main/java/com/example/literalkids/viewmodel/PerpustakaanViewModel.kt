package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.literalkids.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Model
data class StoryShelfData(
    val id: Int,
    val title: String,
    val storyCount: Int,
    val coverImage: Int,
    val isCustom: Boolean = false
)

data class LibraryUiState(
    val shelves: List<StoryShelfData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Repository
class LibraryRepository {
    fun getShelves(): List<StoryShelfData> {
        return listOf(
            StoryShelfData(
                id = 1,
                title = "Sedang Dibaca",
                storyCount = 0,
                coverImage = R.drawable.sample_sedang_dibaca
            ),
            StoryShelfData(
                id = 2,
                title = "Favorit",
                storyCount = 0,
                coverImage = R.drawable.sample_favorit
            ),
            StoryShelfData(
                id = 3,
                title = "Selesai Dibaca",
                storyCount = 0,
                coverImage = R.drawable.sample_selesai_dibaca
            )
        )
    }
}

// ViewModel
class LibraryViewModel(
    private val repository: LibraryRepository = LibraryRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState(isLoading = true))
    val uiState: StateFlow<LibraryUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val shelves = repository.getShelves()
                _uiState.value = LibraryUiState(
                    shelves = shelves,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = LibraryUiState(
                    isLoading = false,
                    error = "Gagal memuat data: ${e.message}"
                )
            }
        }
    }

    fun addStoryToShelf(shelfId: Int) {
        val updatedShelves = _uiState.value.shelves.map { shelf ->
            if (shelf.id == shelfId) {
                shelf.copy(storyCount = shelf.storyCount + 1)
            } else {
                shelf
            }
        }
        _uiState.value = _uiState.value.copy(shelves = updatedShelves)
    }

    fun createNewCollection(name: String) {
        val newShelf = StoryShelfData(
            id = _uiState.value.shelves.size + 1,
            title = name,
            storyCount = 0,
            coverImage = R.drawable.sample_sedang_dibaca, // Ganti dengan gambar default
            isCustom = true
        )
        _uiState.value = _uiState.value.copy(
            shelves = _uiState.value.shelves + newShelf
        )
    }
}