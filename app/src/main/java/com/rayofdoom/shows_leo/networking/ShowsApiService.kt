package com.rayofdoom.shows_leo.networking

import com.rayofdoom.shows_leo.model.network_models.request.LoginRequest
import com.rayofdoom.shows_leo.model.network_models.response.LoginResponse
import com.rayofdoom.shows_leo.model.network_models.request.RegisterRequest
import com.rayofdoom.shows_leo.model.network_models.request.ShowsRequest
import com.rayofdoom.shows_leo.model.network_models.response.RegisterResponse
import com.rayofdoom.shows_leo.model.network_models.response.ShowsResponse
import retrofit2.Call
import retrofit2.http.*

private const val TOKEN_TYPE = "Bearer"
private const val ACCESS_TOKEN = "token-type"
private const val CLIENT = "token-type"
private const val UID = "uid"

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/shows")
    fun fetch(
        @Header("token-type") tokenType: String,
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String
    ): Call<ShowsResponse>
}