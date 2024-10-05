package com.example.w22

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

data class SignInRequest(val phoneNumber: String)
data class SignInResponse(val message: String)
interface ApiService {

    @POST("users/signup")
    fun signUp(@Body user: User): Call<User>
    @POST("/api/users/signin")
    fun signIn(@Body request: SignInRequest): Call<SignInResponse>

    @POST("users/select-place")
    suspend fun selectPlace(@Body placeRequest: PlaceRequest): Response<Unit>
    @POST("/api/users/select-pack")
    fun selectPack(@Body request: SelectPackRequest): Call<ApiResponse>
    @GET("/api/packs")
    fun getPacks(): Call<ApiResponsee<List<Pack>>>
}
data class SelectPackRequest(
    val phoneNumber: String,
    val packId: String
)

data class ApiResponse(
    val success: Boolean,
    val message: String // Adjust this according to your API response
)

data class PlaceRequest(
    val phoneNumber: String,
    val placeNumber: String
)
data class ApiResponsee<T>(
    val success: Boolean,
    val data: T
)

