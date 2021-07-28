package com.rayofdoom.shows_leo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int,
    @SerialName("email") val email: String,
    @SerialName("image_url") val imageUrl: String?
)