package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.literalkids.R
import com.example.literalkids.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Model
data class SearchItem(
    val name: String,
    val icon: Int
)

data class SearchUiState(
    val searchQuery: String = "",
    val searchHistory: List<String> = emptyList(),
    val genres: List<SearchItem> = emptyList(),
    val regions: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Repository
class SearchRepository {
    fun getGenres(): List<SearchItem> {
        return listOf(
            SearchItem("Dongeng", R.drawable.dongeng),
            SearchItem("Sains", R.drawable.sains),
            SearchItem("Fabel", R.drawable.fabel),
            SearchItem("Petualangan", R.drawable.petualangan),
            SearchItem("Komedi", R.drawable.komedi),
            SearchItem("Legenda", R.drawable.legenda),
            SearchItem("Mitos", R.drawable.mitos),
            SearchItem("Slice Of Life", R.drawable.sliceoflife)
        )
    }

    fun getRegions(): List<SearchItem> {
        return listOf(
            SearchItem("Sumatera", R.drawable.sumatera),
            SearchItem("Sulawesi", R.drawable.sulawesi),
            SearchItem("Jawa", R.drawable.jawa),
            SearchItem("Kalimantan", R.drawable.kalimantan),
            SearchItem("Papua", R.drawable.papua),
            SearchItem("Bali", R.drawable.bali),
            SearchItem("Nusa Tenggara", R.drawable.nusatenggara)
        )
    }
}

// ViewModel
class SearchViewModel(
    private val repository: SearchRepository = SearchRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState(isLoading = true))
    val uiState: StateFlow<SearchUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val genres = repository.getGenres()
                val regions = repository.getRegions()
                _uiState.value = SearchUiState(
                    genres = genres,
                    regions = regions,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = SearchUiState(
                    isLoading = false,
                    error = "Gagal memuat data: ${e.message}"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isNotEmpty()) {
            // Simulasi pencarian: tambahkan ke riwayat jika query baru
            if (!_uiState.value.searchHistory.contains(query)) {
                _uiState.value = _uiState.value.copy(
                    searchHistory = listOf(query) + _uiState.value.searchHistory.take(5) // Batasi 5 riwayat
                )
            }
        }
    }

    fun navigateToGenre(navController: NavHostController, genre: String) {
        navController.navigate(Screen.GenreSelector.createRoute(genre))
    }

    fun navigateToRegion(navController: NavHostController, region: String) {
        navController.navigate("region/$region")
    }
}