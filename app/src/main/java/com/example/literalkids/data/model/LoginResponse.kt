package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginInfo(
    @SerializedName("id")
    val id: Long,

    @SerializedName("email")
    val email: String
)

data class LoginResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("user")
    val user: UserLoginInfo
)