package com.example.literalkids.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.literalkids.data.model.DetailArticleModel
import kotlinx.coroutines.launch

class ArticleDetailViewModel : ViewModel() {

    // Simulasi data komentar
    private val _comments = mutableListOf<DetailArticleModel>()
    val comments: List<DetailArticleModel> get() = _comments

    init {
        loadComments() // Memuat komentar awal
    }

    // Fungsi untuk memuat data komentar
    private fun loadComments() {
        viewModelScope.launch {
            // Simulasi memuat data komentar
            _comments.addAll(
                listOf(
                    DetailArticleModel("Rina Sumala", "3 Hari Yang Lalu", "Wah, bener banget! aku mulai rutin bacain buku buat anakku sebelum tidur, sekarang dia malah yang minta dibacain terus setiap malam. ❤ buku cerita bergambar memang jadi favoritnya!", 45, 2),
                    DetailArticleModel("Rina Sumala", "3 Hari Yang Lalu", "Sama, bu! anak saya juga paling semangat kalau tokohnya lucu dan bisa ikut suara-suara hewan. jadi bonding banget ya waktu bacain bareng!", 14, 0)
                )
            )
        }
    }
}