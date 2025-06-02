package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import com.example.literalkids.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeBacaCeritaUiState(
    val storyTitle: String,
    val genre: String,
    val synopsis: String,
    val readCount: String,
    val favoriteLabel: String,
    val xpReward: String,
    val coinReward: String
)

class HomeBacaCeritaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeBacaCeritaUiState(
            storyTitle = "Kancil Mencuri Ketimun",
            genre = "Komedi",
            synopsis = "Si Kancil yang cerdik menyelinap ke kebun petani untuk mencuri ketimun segar. Namun, ia tertangkap jebakan yang dipasang oleh petani. Dengan akalnya, kancil berpura-pura menyesal dan menipu petani hingga akhirnya berhasil melarikan diri.",
            readCount = "4,2 Ribu Dibaca",
            favoriteLabel = "Favorit",
            xpReward = "+60 xp",
            coinReward = "+20 koin"
        )
    )
    val uiState: StateFlow<HomeBacaCeritaUiState> = _uiState.asStateFlow()

    fun onXpButtonClicked() {
        // TODO: Implementasi logika untuk tombol XP
    }

    fun onCoinButtonClicked() {
        // TODO: Implementasi logika untuk tombol koin
    }
}