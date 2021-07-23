package com.rayofdoom.shows_leo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.fillShowsData

class ShowsViewModel : ViewModel() {
    private val shows = fillShowsData()
    private val showsLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
    }
    fun getShowsLiveData(): LiveData<List<Show>>{
        return showsLiveData
    }

    public fun initShows(){
        showsLiveData.value = shows
    }

}