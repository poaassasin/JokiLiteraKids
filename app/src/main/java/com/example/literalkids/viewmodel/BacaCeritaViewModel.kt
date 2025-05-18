package com.example.literalkids.viewmodel

import androidx.lifecycle.ViewModel
import com.example.literalkids.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Model data
data class CeritaPage(
    val title: String,
    val text: String,
    val backgroundRes: Int
)

data class BacaCeritaUiState(
    val ceritaPages: List<CeritaPage>,
    val currentPage: Int,
    val isSpeaking: Boolean,
    val showFinishDialog: Boolean
) {
    val currentCerita: CeritaPage
        get() = ceritaPages[currentPage]
}

class BacaCeritaViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        BacaCeritaUiState(
            ceritaPages = listOf(
                CeritaPage(
                    "1 - Rasa Lapar Ditengah Hutan",
                    "Si Kancil yang cerdik menyelinap ke kebun petani untuk mencuri ketimun segar. Namun, ia tertangkap jebakan yang dipasang oleh petani. Dengan akalnya, Kancil berpura-pura menyesal dan menipu petani hingga akhirnya berhasil melarikan diri.",
                    R.drawable.bg_ilustrasi1
                ),
                CeritaPage(
                    "2 - Rencana Diam-Diam",
                    "Si Kancil tahu kebun itu milik Pak Tani. Tapi rasa lapar membuatnya nekat. Malam hari, saat hutan mulai sunyi, Kancil menyelinap masuk lewat celah pagar. Ia memetik satu ketimun besar dan melahapnya cepat-cepat. “Hmmm… manis dan segar!” katanya senang. Ia kembali ke hutan dengan perut kenyang. Besoknya, Kancil datang lagi, dan lagi. Ia bahkan mulai membawa teman-temannya diam-diam.",
                    R.drawable.bg_ilustrasi2
                ),
                CeritaPage(
                    "3 - Pak Tani Curiga",
                    "Setiap pagi, Pak Tani merasa jumlah ketimunnya semakin berkurang. “Siapa yang mencuri hasil kebunku?” gerutunya. Lalu ia membuat rencana. Ia memasang jebakan dari jaring dan tali di tengah kebun, di sekitar ketimun paling besar. “Kalau pencuri datang lagi, pasti tertangkap!” ujar Pak Tani yakin.",
                    R.drawable.bg_ilustrasi3
                ),
                CeritaPage(
                    "4 - Tertangkap",
                    "Malam pun tiba. Kancil kembali, kali ini sendirian. Ia melompat girang saat melihat ketimun favoritnya masih ada. “Ini pasti hadiah untukku,” katanya bangga. Tapi begitu ia melangkah... ZAPP! Kakinya terjerat jaring! Kancil panik dan berusaha melepaskan diri, tapi sia-sia. Ia terjebak hingga pagi.",
                    R.drawable.bg_ilustrasi4
                ),
                CeritaPage(
                    "5 - Belajar Dari Kesalahan",
                    "Pak Tani datang membawa keranjang. Ia terkejut melihat seekor kancil kecil yang gemetar ketakutan. Namun Pak Tani tidak marah. “Kamu lapar, ya? Tapi mencuri itu tidak benar,” katanya lembut. Ia melepaskan Kancil, lalu berkata, “Lain kali, mintalah baik-baik.” Sejak saat itu, Kancil tak pernah mencuri lagi. Ia belajar mencari makanan dengan cara yang jujur dan mulai menanam ketimun sendiri.",
                    R.drawable.bg_ilustrasi5
                )
            ),
            currentPage = 0,
            isSpeaking = false,
            showFinishDialog = false
        )
    )
    val uiState: StateFlow<BacaCeritaUiState> = _uiState.asStateFlow()

    fun setPage(page: Int) {
        val newPage = page.coerceIn(0, _uiState.value.ceritaPages.lastIndex)
        _uiState.value = _uiState.value.copy(
            currentPage = newPage,
            isSpeaking = false // Stop TTS saat ganti halaman
        )
    }

    fun toggleSpeaking() {
        _uiState.value = _uiState.value.copy(
            isSpeaking = !_uiState.value.isSpeaking
        )
    }

    fun showFinishDialog() {
        _uiState.value = _uiState.value.copy(showFinishDialog = true)
    }

    fun hideFinishDialog() {
        _uiState.value = _uiState.value.copy(showFinishDialog = false)
    }
}