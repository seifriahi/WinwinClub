package com.example.w22

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response

data class SignInRequest(val phoneNumber: String)
data class SignInResponse(val message: String)
interface ApiService {

    @POST("users/signup")
    fun signUp(@Body user: User): Call<User>
    @POST("/api/users/signin")
    fun signIn(@Body request: SignInRequest): Call<SignInResponse>

    @POST("users/select-place")
    suspend fun selectPlace(@Body placeRequest: PlaceRequest): Response<Void>
}
data class PlaceRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("placeNumber") val placeNumber: String
)
