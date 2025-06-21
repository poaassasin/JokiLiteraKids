package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("error")
    val message: String
)