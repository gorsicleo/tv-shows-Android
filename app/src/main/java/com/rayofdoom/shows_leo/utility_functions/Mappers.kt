package com.rayofdoom.shows_leo.utility_functions

import com.rayofdoom.shows_leo.database.entities.ReviewEntity
import com.rayofdoom.shows_leo.database.entities.ShowEntity
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.User


fun List<ShowEntity>.mapToShowsList(): List<Show> {
    return this.map {
        Show(
            it.showId,
            it.showTitle,
            it.showDescription,
            it.imageResource,
            it.averageRating,
            it.noOfReviews
        )
    }
}

fun List<Show>.mapToShowsEntityList(): List<ShowEntity> {
    return this.map {
        ShowEntity(
            it.showId,
            it.showTitle,
            it.showDescription,
            it.imageResource,
            it.averageRating,
            it.noOfReviews
        )
    }
}

fun ShowEntity.mapToShow(): Show {
    return Show(
        this.showId,
        this.showTitle,
        this.showDescription,
        this.imageResource,
        this.averageRating,
        this.noOfReviews
    )
}

fun ReviewEntity.mapToReview(): Review {
    return Review(
        this.reviewId,
        this.comment,
        this.rating,
        this.showId,
        this.user
    )
}

fun Review.mapToReviewEntity(): ReviewEntity {
    return ReviewEntity(
        this.id,
        this.comment,
        this.rating,
        this.showId,
        this.user
    )
}

fun List<ReviewEntity>.mapToReviewsList(): List<Review> {
    return this.map {
        Review(
            it.reviewId,
            it.comment,
            it.rating,
            it.showId,
            it.user
        )
    }
}

fun List<Review>.mapToReviewEntityList(): List<ReviewEntity> {
    return this.map {
        ReviewEntity(
            it.id,
            it.comment,
            it.rating,
            it.showId,
            it.user
        )
    }
}

