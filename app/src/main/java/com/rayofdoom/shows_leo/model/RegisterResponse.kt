package com.rayofdoom.shows_leo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponse(
    @SerialName("user") val user: User
)