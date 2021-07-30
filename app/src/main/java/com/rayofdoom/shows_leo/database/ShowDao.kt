package com.rayofdoom.shows_leo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rayofdoom.shows_leo.database.entities.ShowEntity


@Dao
interface ShowDao {

    @Query("SELECT * FROM shows")
    fun getAllShows(): LiveData<List<ShowEntity>>

    @Query("SELECT * FROM shows WHERE id IS :showId")
    fun getShow(showId: String): LiveData<ShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllShows(shows: List<ShowEntity>)

}