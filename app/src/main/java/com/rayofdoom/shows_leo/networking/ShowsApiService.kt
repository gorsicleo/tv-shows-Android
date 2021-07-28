package com.rayofdoom.shows_leo.networking

import com.rayofdoom.shows_leo.model.network_models.request.LoginRequest
import com.rayofdoom.shows_leo.model.network_models.request.RegisterRequest
import com.rayofdoom.shows_leo.model.network_models.request.ReviewRequest
import com.rayofdoom.shows_leo.model.network_models.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/shows")
    fun fetchShows(): Call<ShowsResponse>

    @GET
    fun fetchShow(@Url url: String): Call<ShowDetailsResponse>

    @GET
    fun fetchReviews(@Url url: String): Call<ReviewsResponse>

    @POST("/reviews")
    fun createReview(@Body request: ReviewRequest): Call<ReviewResponse>

    @Multipart
    @PUT("/users")
    fun uploadPicture(@Part image: MultipartBody.Part): Call<LoginResponse>
}