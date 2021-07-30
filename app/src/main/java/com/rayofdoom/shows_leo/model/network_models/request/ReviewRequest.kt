package com.rayofdoom.shows_leo.model.network_models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest(
    @SerialName("rating") val rating: Int,
    @SerialName("comment") val comment: String,
    @SerialName("show_id") val showId: Int
)