package com.rayofdoom.shows_leo.model.network_models.response


import com.rayofdoom.shows_leo.model.Meta
import com.rayofdoom.shows_leo.model.Pagination
import com.rayofdoom.shows_leo.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsResponse(
    @SerialName("reviews") val reviews: List<Review>,
    @SerialName("meta") val meta: Meta
)