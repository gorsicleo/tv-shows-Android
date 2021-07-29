package com.rayofdoom.shows_leo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="shows")
data class ShowEntity (
    @PrimaryKey @ColumnInfo(name="id")val showId: Int,
    @ColumnInfo(name="title")val showTitle: String,
    @ColumnInfo(name="description")val showDescription: String?,
    @ColumnInfo(name="image_url")val imageResource: String?,
    @ColumnInfo(name="average_rating")val averageRating: Double?,
    @ColumnInfo(name="no_of_reviews")val noOfReviews: Int
)
