package com.rayofdoom.shows_leo.model.network_models.response

import com.rayofdoom.shows_leo.model.Meta
import com.rayofdoom.shows_leo.model.Pagination
import com.rayofdoom.shows_leo.model.Show
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowsResponse(
    @SerialName("shows") val shows: List<Show>,
    @SerialName("meta") val meta: Meta
)