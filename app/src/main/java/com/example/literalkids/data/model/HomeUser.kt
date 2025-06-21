package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

// Model ini untuk data inti pengguna
// Cocok dengan struktur: { "name": "...", "username": "...", "level": ..., "progress": ... }
data class HomeUser(
    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("level")
    val level: Int,

    @SerializedName("progress")
    val progress: Float
)