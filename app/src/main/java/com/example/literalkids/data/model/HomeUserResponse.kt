package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

// Model ini berfungsi sebagai 'pembungkus' sesuai dengan respon API Anda: { "user": { ... } }
data class HomeUserResponse(
    @SerializedName("user")
    val user: HomeUser
)