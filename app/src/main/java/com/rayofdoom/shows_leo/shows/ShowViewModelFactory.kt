package com.rayofdoom.shows_leo.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rayofdoom.shows_leo.database.ShowsDatabase
import java.lang.IllegalArgumentException

private const val ERROR = "Class cannot be assigned to ShowsViewModel"

class ShowViewModelFactory(val database: ShowsDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowsViewModel::class.java)){
            return ShowsViewModel(database) as T
        } else {
            throw IllegalArgumentException(ERROR)
        }
    }
}