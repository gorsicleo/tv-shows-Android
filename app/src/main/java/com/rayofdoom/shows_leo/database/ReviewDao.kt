package com.rayofdoom.shows_leo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rayofdoom.shows_leo.database.entities.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE show_id = :showId")
    fun getReviews(showId: String): LiveData<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(review: ReviewEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllReviews(reviews: List<ReviewEntity>)


}