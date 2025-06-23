package com.example.literalkids.data.repository

import com.example.literalkids.data.model.Story
import com.example.literalkids.R
import com.example.literalkids.data.model.HomeUser
import android.util.Log
import com.example.literalkids.data.local.UserDao
import com.example.literalkids.data.model.ApiErrorResponse
import com.example.literalkids.data.network.ApiService
import com.example.literalkids.data.network.UserSyncRequest
import com.example.literalkids.data.repository.DataResult // Pastikan import ini benar
import com.google.gson.Gson

class StoryRepository(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
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
                val userFromApi = response.body()?.user
                if (userFromApi != null) {
                    userFromApi.userId = userId

                    Log.d("StoryRepository", "Data user dari API berhasil diambil. Menyimpan ke cache...")
                    userDao.insertOrUpdateUser(userFromApi)

                    // Jika sukses dan data ada, kembalikan data pengguna
                    return DataResult.Success(userFromApi)
                } else {
                    // Handle kasus aneh di mana respon sukses tapi body-nya kosong
                    return DataResult.Failure("Data pengguna tidak ditemukan dalam respon.")
                }
            }
            return fetchUserFromCache(userId, "Gagal mengambil data dari server.")
        } catch (e: Exception) {
            // Handle jika terjadi error jaringan (tidak ada internet, timeout, dll.)
            Log.w("StoryRepository", "Koneksi ke API gagal. Mencoba mengambil dari cache lokal...")
            return fetchUserFromCache(userId, "Anda sedang offline. Menampilkan data terakhir.")
        }
    }

    suspend fun syncOfflineData() {
        // 1. Ambil semua data 'kotor' dari Room
        val unsyncedUsers = userDao.getUnsyncedUsers() // Anda perlu membuat query ini di UserDao

        for (user in unsyncedUsers) {
            // --- PERBAIKAN DI SINI ---
            // 2. Buat objek UserSyncRequest terlebih dahulu
            val syncRequest = UserSyncRequest(level = user.level, progress = user.progress)

            // 3. Kirim objek tersebut sebagai parameter kedua
            val updateResponse = apiService.syncUserData(user.userId, syncRequest)
            // --- SELESAI PERBAIKAN ---

            if (updateResponse.isSuccessful) {
                // 4. Jika API sukses, tandai data sebagai 'sudah sinkron'
                val syncedUser = user.copy(isSynced = true)
                userDao.insertOrUpdateUser(syncedUser)
                Log.d("StoryRepository", "User ${user.userId} synced successfully.")
            } else {
                Log.e("StoryRepository", "Failed to sync user ${user.userId}.")
                // Anda bisa menambahkan logika error handling di sini
            }
        }
    }



    suspend fun updateUserProgressOffline(userId: Long, newProgress: Float) {
        // 1. Ambil data user saat ini dari cache
        val currentUser = userDao.getUserById(userId)
        if (currentUser != null) {
            // 2. Buat objek baru dengan progres yang diperbarui dan tandai sebagai 'belum sinkron'
            val updatedUser = currentUser.copy(
                progress = newProgress,
                isSynced = false // Data ini 'kotor'
            )
            // 3. Simpan kembali ke database Room
            userDao.insertOrUpdateUser(updatedUser)
        }
    }

    private suspend fun fetchUserFromCache(userId: Long, failureMessage: String): DataResult<HomeUser> {
        val cachedUser = userDao.getUserById(userId)
        return if (cachedUser != null) {
            // LANGKAH 2: Response lokal ditampilkan di UI
            Log.d("StoryRepository", "Berhasil mengambil data user dari cache.")
            DataResult.Success(cachedUser)
        } else {
            // Jika di cache juga tidak ada, baru kita menyerah
            DataResult.Failure(failureMessage)
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