package com.rayofdoom.shows_leo.networking

import com.rayofdoom.shows_leo.model.LoginRequest
import com.rayofdoom.shows_leo.model.LoginResponse
import com.rayofdoom.shows_leo.model.RegisterRequest
import com.rayofdoom.shows_leo.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}