package com.example.literalkids.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.example.literalkids.data.local.TokenManager
import okhttp3.Interceptor
import android.content.Context
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val requestBuilder = chain.request().newBuilder()
        tokenManager.getToken()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}

object ApiClient {

    // URL dasar dari API Anda di Railway (tanpa /login, dll)
    private const val BASE_URL = "https://literakids-api.up.railway.app/api/"

    fun getApiService(context: Context): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val tokenManager = TokenManager(context)
        val authInterceptor = AuthInterceptor(tokenManager)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Membuat instance Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Mengembalikan implementasi dari ApiService interface
        return retrofit.create(ApiService::class.java)
    }
}