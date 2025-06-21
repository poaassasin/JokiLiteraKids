package com.example.literalkids.data.repository

import com.example.literalkids.data.model.Story
import com.example.literalkids.R
import com.example.literalkids.data.model.HomeUser
import android.util.Log
import com.example.literalkids.data.model.ApiErrorResponse
import com.example.literalkids.data.network.ApiService
import com.example.literalkids.data.repository.DataResult // Pastikan import ini benar
import com.google.gson.Gson

class StoryRepository(private val apiService: ApiService) {
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

    suspend fun getHomepageUser(userId: Long): DataResult<HomeUser> {
        try {
            // Melakukan panggilan API yang sebenarnya melalui ApiService
            val response = apiService.getHomepageUser(userId)

            // Cek jika respon dari server sukses (HTTP code 2xx)
            if (response.isSuccessful) {
                // Ambil '.user' dari body karena respon JSON-nya di-wrap: { "user": {...} }
                val user = response.body()?.user
                if (user != null) {
                    // Jika sukses dan data ada, kembalikan data pengguna
                    return DataResult.Success(user)
                } else {
                    // Handle kasus aneh di mana respon sukses tapi body-nya kosong
                    return DataResult.Failure("Data pengguna tidak ditemukan dalam respon.")
                }
            } else {
                // Handle jika server merespon dengan error (HTTP code 4xx atau 5xx)
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    // Coba parse pesan error dari JSON server
                    Gson().fromJson(errorBody, ApiErrorResponse::class.java).message
                } catch (e: Exception) {
                    // Pesan fallback jika parsing JSON error gagal
                    "Gagal mengambil data pengguna"
                }
                return DataResult.Failure(errorMessage)
            }
        } catch (e: Exception) {
            // Handle jika terjadi error jaringan (tidak ada internet, timeout, dll.)
            Log.e("StoryRepository", "Error Jaringan saat mengambil data user: ${e.message}", e)
            return DataResult.NetworkError
        }
    }

    fun getBannerImages(): List<Int> {
        return listOf(
            R.drawable.banner_kancil,
            R.drawable.banner_istana,
            R.drawable.banner_pohon
        )
    }
}