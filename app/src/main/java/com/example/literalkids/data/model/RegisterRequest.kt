package com.example.literalkids.data.model

// Data yang dikirim ke server saat registrasi
data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmpassword: String // Nama harus sama persis dengan di backend
)