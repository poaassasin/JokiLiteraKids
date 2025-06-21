//package com.example.literalkids.data.model
//
//// Struktur data untuk pengguna
//data class User(
//    val id: Int,
//    val email: String,
//    val password: String
//)
//
//// Struktur data untuk state UI autentikasi
//data class AuthUiState(
//    val email: String = "",
//    val password: String = "",
//    val confirmPassword: String = "", // Untuk register
//    val isEmailValid: Boolean = false,
//    val isPasswordValid: Boolean = false,
//    val isConfirmPasswordValid: Boolean = false,
//    val isLoading: Boolean = false,
//    val error: String? = null,
//    val isAuthenticated: Boolean = false
//)
//
//// Repository untuk menangani logika autentikasi
//class AuthRepository {
//    // Simulasi penyimpanan user (data statis untuk contoh)
//    private val registeredUsers = mutableListOf<User>()
//
//    // Validasi email
//    fun validateEmail(email: String): Boolean {
//        return email.contains("@") && email.contains(".") && email.isNotBlank()
//    }
//
//    // Validasi password
//    fun validatePassword(password: String): Boolean {
//        return password.length >= 6 && password.isNotBlank()
//    }
//
//    // Validasi konfirmasi password
//    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
//        return password == confirmPassword && confirmPassword.isNotBlank()
//    }
//
//    // Simulasi register
//    fun register(email: String, password: String): Result<User> {
//        return if (validateEmail(email) && validatePassword(password)) {
//            if (registeredUsers.any { it.email == email }) {
//                Result.failure(Exception("Email sudah terdaftar"))
//            } else {
//                val newUser = User(
//                    id = registeredUsers.size + 1,
//                    email = email,
//                    password = password
//                )
//                registeredUsers.add(newUser)
//                Result.success(newUser)
//            }
//        } else {
//            Result.failure(Exception("Email atau password tidak valid"))
//        }
//    }
//
//    // Simulasi login
//    fun login(email: String, password: String): Result<User> {
//        return if (validateEmail(email) && validatePassword(password)) {
//            val user = registeredUsers.find { it.email == email && it.password == password }
//            if (user != null) {
//                Result.success(user)
//            } else {
//                Result.failure(Exception("Email atau password salah"))
//            }
//        } else {
//            Result.failure(Exception("Email atau password tidak valid"))
//        }
//    }
//}