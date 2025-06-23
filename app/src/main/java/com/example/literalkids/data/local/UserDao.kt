package com.example.literalkids.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.literalkids.data.model.HomeUser

@Dao
interface UserDao {

    /**
     * Menyimpan atau memperbarui data HomeUser.
     * Jika data dengan userId yang sama sudah ada, data lama akan diganti (REPLACE).
     * Ini sangat berguna saat kita mengambil data baru dari API dan ingin menyimpannya ke cache.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: HomeUser)

    /**
     * Mengambil data HomeUser dari cache lokal berdasarkan userId.
     * Dibuat nullable (?) karena mungkin saja data belum ada di cache saat pertama kali dibuka.
     */
    @Query("SELECT * FROM homepage_user_cache WHERE userId = :userId")
    suspend fun getUserById(userId: Long): HomeUser?

    @Query("SELECT * FROM homepage_user_cache WHERE isSynced = 0")
    suspend fun getUnsyncedUsers(): List<HomeUser>
    /**
     * Menghapus data user dari cache (misalnya saat pengguna logout).
     */
    @Query("DELETE FROM homepage_user_cache WHERE userId = :userId")
    suspend fun deleteUser(userId: Long)
}
