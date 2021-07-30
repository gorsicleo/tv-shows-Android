package com.rayofdoom.shows_leo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rayofdoom.shows_leo.database.entities.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE show_id = :showId")
    fun getReviewsByShowId(showId: String): LiveData<List<ReviewEntity>>

    @Query("SELECT * FROM reviews")
    fun getAllReviews(): LiveData<List<ReviewEntity>>

    @Query("SELECT * FROM reviews WHERE review_id <= 0 AND show_id = :showId")
    fun getAllUnuploadedReviews(showId: String): LiveData<List<ReviewEntity>>

    @Delete
    fun deleteReview(review: ReviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(review: ReviewEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllReviews(reviews: List<ReviewEntity>)


}