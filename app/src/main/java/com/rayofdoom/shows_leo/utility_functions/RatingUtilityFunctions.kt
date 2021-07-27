package com.rayofdoom.shows_leo.utility_functions

import android.widget.Toast
import com.rayofdoom.shows_leo.model.CumulativeRatingForShow
import com.rayofdoom.shows_leo.model.Review
import java.util.*

fun List<Review>.getCumulativeRatingForShow(): CumulativeRatingForShow {
    val average = this.map { review -> review.userRating }.average()
    return CumulativeRatingForShow(this.size, average)
}