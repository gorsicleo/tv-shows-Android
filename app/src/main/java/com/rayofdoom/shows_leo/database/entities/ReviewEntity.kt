package com.rayofdoom.shows_leo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rayofdoom.shows_leo.model.User

@Entity(tableName="reviews")
data class ReviewEntity (
    @PrimaryKey @ColumnInfo(name="review_id")val reviewId: Int,
    @ColumnInfo(name="comment")val comment: String,
    @ColumnInfo(name="rating")val rating: Int,
    @ColumnInfo(name="show_id")val showId: Int,
    @Embedded val user: User
)