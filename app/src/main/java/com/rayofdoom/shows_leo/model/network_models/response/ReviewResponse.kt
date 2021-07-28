package com.rayofdoom.shows_leo.model.network_models.response

import com.rayofdoom.shows_leo.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    @SerialName("review") val review: Review
)