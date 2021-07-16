package com.rayofdoom.shows_leo.model

import androidx.annotation.DrawableRes

data class Review(
    val userName: String,
    val userReview: String,
    @DrawableRes val userProfilePicture: Int,
    val userRating: Double
)