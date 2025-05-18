package com.example.literalkids.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    val isEmailValid: Boolean
        get() = email.contains("@") && email.contains(".")
}
