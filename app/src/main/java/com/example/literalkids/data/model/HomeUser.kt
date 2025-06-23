package com.example.literalkids.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Model ini untuk data inti pengguna
// Cocok dengan struktur: { "name": "...", "username": "...", "level": ..., "progress": ... }
@Entity(tableName = "homepage_user_cache")
data class HomeUser(
    @PrimaryKey
    var userId: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("level")
    val level: Int,

    @SerializedName("progress")
    val progress: Float,

    var isSynced: Boolean = true
)