package com.rayofdoom.shows_leo.model.network_models.response

import com.rayofdoom.shows_leo.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponse(
    @SerialName("user") val user: User
)