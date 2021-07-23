package com.rayofdoom.shows_leo.utility_functions

import com.rayofdoom.shows_leo.model.CumulativeRatingForShow
import com.rayofdoom.shows_leo.model.Review

private const val EMAIL_USERNAME_SEPARATOR = "@"


fun List<Review>.getCumulativeRatingForShow(): CumulativeRatingForShow {
    return if (this.isEmpty()) {
        CumulativeRatingForShow(this.size, 0.0)
    } else {
        val average = this.map { review -> review.userRating }.average()
        CumulativeRatingForShow(this.size, average)
    }
}


fun String.parseUsernameFromEmail(): String {
    return this.subSequence(0, this.indexOf(EMAIL_USERNAME_SEPARATOR)).toString()
}