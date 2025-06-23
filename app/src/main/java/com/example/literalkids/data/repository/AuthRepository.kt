package com.example.literalkids.data.repository

import android.util.Log
import com.example.literalkids.data.local.TokenManager
import com.example.literalkids.data.model.ApiErrorResponse
import com.example.literalkids.data.model.LoginRequest
import com.example.literalkids.data.model.OnboardingModel
import com.example.literalkids.data.model.RegisterRequest
import com.example.literalkids.data.network.ApiService
import com.google.gson.Gson

// Kita buat sealed class untuk hasil yang lebih deskriptif daripada Boolean
sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val errorMessage: String) : AuthResult()
    object NetworkError : AuthResult()
}

// Repository bergantung pada ApiService dan TokenManager (ini disebut Dependency Injection)
open class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {

    // Ini adalah satu-satunya fungsi yang akan dilihat oleh ViewModel nanti
    suspend fun loginUser(request: LoginRequest): AuthResult {
        try {
            // 1. Panggil fungsi login dari ApiService
            val response = apiService.login(request)

            // 2. Cek apakah panggilan API berhasil (status code 2xx)
            if (response.isSuccessful) {
                val loginResponse = response.body()

                loginResponse?.user?.let { user ->
                    // Jika user tidak null, simpan token dan ID
                    tokenManager.saveToken(loginResponse.token)
                    tokenManager.saveUserId(user.id)
                    return AuthResult.Success
                }
                // Jika sampai sini, berarti ada masalah dengan respon server
                return AuthResult.Failure("Gagal memproses data login.")

                // Pastikan body respon tidak kosong
//                if (loginResponse != null) {
//                    // 3. Jika berhasil, SIMPAN TOKEN!
//                    tokenManager.saveToken(loginResponse.token)
//                    Log.i("AuthRepository", "Login successful, token saved.")
//                    tokenManager.saveUserId(loginResponse.user.id)
//                    return AuthResult.Success
//                } else {
//                    // Ini jarang terjadi, tapi sebagai pengaman
//                    Log.e("AuthRepository", "Login response body is null.")
//                    return AuthResult.Failure("Respon tidak valid dari server.")
//                }
            } else {
                // 4. Jika gagal (status code 4xx atau 5xx), parse pesan error dari server
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    Gson().fromJson(errorBody, ApiErrorResponse::class.java).message
                } catch (e: Exception) {
                    "Email atau password salah"
                }
                Log.w("AuthRepository", "Login failed: $errorMessage")
                return AuthResult.Failure(errorMessage)
            }
        } catch (e: Exception) {
            // 5. Jika terjadi error koneksi (misal: tidak ada internet)
            Log.e("AuthRepository", "Network error during login", e)
            return AuthResult.NetworkError
        }
    }

    suspend fun registerUser(request: RegisterRequest): AuthResult {
        // Validasi sederhana di sisi klien sebelum mengirim ke server
        if (request.password != request.confirmpassword) {
            return AuthResult.Failure("Password dan konfirmasi tidak cocok")
        }

        return try {
            val response = apiService.register(request)
            if (response.isSuccessful) {
                Log.i("AuthRepository", "Registration successful.")
                AuthResult.Success
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = Gson().fromJson(errorBody, ApiErrorResponse::class.java).message
                Log.w("AuthRepository", "Registration failed: $errorMessage")
                AuthResult.Failure(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Network error during registration", e)
            AuthResult.NetworkError
        }
    }

    open fun getLoggedInUserId(): Long? {
        return tokenManager.getUserId()
    }

    suspend fun submitOnboarding(data: OnboardingModel): AuthResult { // Kita bisa pakai ulang sealed class AuthResult
        return try {
            // 1. Panggil fungsi dari ApiService
            val response = apiService.submitOnboardingData(data)

            // 2. Cek apakah sukses
            if (response.isSuccessful) {
                val onboardingResponse = response.body()
                if (onboardingResponse != null) {
                    // Setelah onboarding, simpan ID yang dikembalikan oleh server
                    tokenManager.saveUserId(onboardingResponse.id) // <-- Simpan ID
                    return AuthResult.Success
                } else {
                    Log.e("AuthRepository", "Onboarding response body is null.")
                    return AuthResult.Failure("Respon tidak valid dari server.")
                }
            } else {
                // 3. Jika gagal, parse errornya
                val responseCode = response.code()
                val errorBody = response.errorBody()?.string()

                // Log ini akan memberitahu kita kode status dan apa isi mentah dari errornya
                Log.e("AuthRepository", "API Call Failed! Code: $responseCode, Raw Error Body: $errorBody")

                val serverErrorMessage = try {
                    // Coba baca pesan error dari JSON
                    Gson().fromJson(errorBody, ApiErrorResponse::class.java)?.message
                } catch (e: Exception) {
                    null
                }
                val errorMessage = serverErrorMessage ?: "Gagal menyimpan data. Kode Error: $responseCode"
                Log.e("AuthRepository", "Processed error message: $errorMessage")
                return AuthResult.Failure(errorMessage)
            }
        } catch (e: Exception) {
            // 4. Tangani error jaringan
            Log.e("AuthRepository", "Network error during onboarding submission", e)
            AuthResult.NetworkError
        }
    }

    // Fungsi untuk logout
    fun logout() {
        tokenManager.clearToken()
    }
}