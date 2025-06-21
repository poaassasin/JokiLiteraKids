package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String
)