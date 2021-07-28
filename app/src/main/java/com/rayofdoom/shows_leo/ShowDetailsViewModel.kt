package com.rayofdoom.shows_leo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.fillReviewData
import com.rayofdoom.shows_leo.utility_functions.fillShowsData

class ShowDetailsViewModel : ViewModel() {

    private var reviews: MutableList<Review> = mutableListOf()
    private var shows: List<Show> =  fillShowsData()
    private val reviewLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }
    private val showDetailsLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
    }

    fun loadDummyReviews(value: Boolean) {
        if (value) {
            reviews.addAll(fillReviewData())
            initReviews()
        } else {
            reviews.removeAll(fillReviewData())
            initReviews()
        }

    }
    fun getShowDetailsLiveData(): LiveData<List<Show>>{
        return showDetailsLiveData
    }

    fun initShowDetailsLiveData() {
        showDetailsLiveData.value = shows
    }


    fun getReviewsLiveData(): LiveData<List<Review>> {
        return reviewLiveData
    }

    fun initReviews() {
        reviewLiveData.value = reviews
    }

    fun addReview(review: Review) {
        reviews.add(review)
        initReviews()
    }


}