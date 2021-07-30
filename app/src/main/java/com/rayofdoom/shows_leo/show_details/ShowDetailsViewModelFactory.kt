package com.rayofdoom.shows_leo.show_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rayofdoom.shows_leo.database.ShowsDatabase
import java.lang.IllegalArgumentException

private const val ERROR = "Class cannot be assigned to ShowDetailsViewModel"

class ShowDetailsViewModelFactory(val database: ShowsDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowDetailsViewModel::class.java)){
            return ShowDetailsViewModel(database) as T
        } else {
            throw IllegalArgumentException(ERROR)
        }
    }
}