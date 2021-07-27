package com.rayofdoom.shows_leo.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Show(
    val showId: Int,
    val showTitle: String,
    @StringRes val showDescription: Int,
    @DrawableRes val imageResource: Int
)