package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

// Data yang diterima dari server saat registrasi sukses
data class RegisterResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("id")
    val id: Long
)