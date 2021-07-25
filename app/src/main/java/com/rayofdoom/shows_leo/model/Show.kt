package com.rayofdoom.shows_leo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Show(
    @SerialName("id")val showId: Int,
    @SerialName("title")val showTitle: String,
    @SerialName("description")val showDescription: String?,
    @SerialName("image_url")val imageResource: String?,
    @SerialName("average_rating")val averageRating: Double?,
    @SerialName("no_of_reviews")val noOfReviews: Int
)