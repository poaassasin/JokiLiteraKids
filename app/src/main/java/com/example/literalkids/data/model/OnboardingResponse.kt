package com.example.literalkids.data.model

import com.google.gson.annotations.SerializedName

data class OnboardingResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("id")
    val id: Long
)