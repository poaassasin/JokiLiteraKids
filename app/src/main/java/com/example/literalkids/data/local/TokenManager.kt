package com.example.literalkids.data.local

import android.content.Context
import android.content.SharedPreferences

// Kelas ini bertanggung jawab penuh untuk menyimpan, membaca, dan menghapus token.
class TokenManager(context: Context) {
    // Membuat file SharedPreferences bernama "app_prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val USER_TOKEN = "user_token"
    }

    // Fungsi untuk menyimpan token
    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    // Fungsi untuk membaca token
    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    // Fungsi untuk menghapus token (saat logout)
    fun clearToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }
}