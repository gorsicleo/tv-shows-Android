package com.rayofdoom.shows_leo.show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.utility_functions.fillReviewData

class ShowDetailsViewModel : ViewModel() {

    private var reviews: MutableList<Review> = mutableListOf()
    private val reviewLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
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