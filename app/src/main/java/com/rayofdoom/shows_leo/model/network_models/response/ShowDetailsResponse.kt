package com.rayofdoom.shows_leo.model.network_models.response

import com.rayofdoom.shows_leo.model.Show
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowDetailsResponse (
    @SerialName("show") val show: Show
    )