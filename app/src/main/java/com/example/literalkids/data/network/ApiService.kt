package com.example.literalkids.data.network

import com.example.literalkids.data.model.HomeUserResponse
import com.example.literalkids.data.model.LoginRequest
import com.example.literalkids.data.model.LoginResponse
import com.example.literalkids.data.model.OnboardingModel
import com.example.literalkids.data.model.OnboardingResponse
import com.example.literalkids.data.model.RegisterRequest
import com.example.literalkids.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class UserSyncRequest(
    val level: Int,
    val progress: Float
)

interface ApiService {

    // Mendefinisikan endpoint POST untuk login
    @POST("login") // Path ini relatif terhadap Base URL Anda
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("onboarding")
    suspend fun submitOnboardingData(@Body onboardingData: OnboardingModel): Response<OnboardingResponse>

    @GET("homepage/{id}")
    suspend fun getHomepageUser(@Path("id") userId: Long): Response<HomeUserResponse>

    @PUT("homepage/sync/{id}")
    suspend fun syncUserData(
        @Path("id") userId: Long,
        @Body syncData: UserSyncRequest
    ): Response<Unit>
}