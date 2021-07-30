package com.rayofdoom.shows_leo

import android.app.Application
import com.rayofdoom.shows_leo.database.ShowsDatabase

class ShowsApp : Application(){
    val showsDatabase by lazy {
        ShowsDatabase.getDatabase(this)
    }
}