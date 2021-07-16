package com.rayofdoom.shows_leo.utility_functions

import com.rayofdoom.shows_leo.model.CumulativeRatingForShow
import com.rayofdoom.shows_leo.model.Review

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun List<Review>.getCumulativeRatingForShow():CumulativeRatingForShow{
    var sum = 0.0
    var count = 0
    this.forEach {
        sum += it.userRating
        count++
    }
    return CumulativeRatingForShow(count, (sum/count))
}