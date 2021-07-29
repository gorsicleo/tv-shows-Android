package com.rayofdoom.shows_leo.utility_functions

import kotlin.math.abs

private const val MILLISECONDS_IN_SECOND = 1000
private const val SECONDS_IN_30_DAYS = 3600*24*30

object IdGenerator {

    fun generateReviewId(): Int{
        return -abs(System.currentTimeMillis().
        div(MILLISECONDS_IN_SECOND).mod(SECONDS_IN_30_DAYS))
    }
}