package com.rayofdoom.shows_leo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.utility_functions.fillReviewData

class ShowDetailsViewModel : ViewModel(){
    //52.19
    private val reviews = fillReviewData()
    private val reviewLiveData : MutableLiveData<List<Review>> by lazy{
        MutableLiveData<List<Review>>()
    }

    fun getReviewsLiveData(): LiveData<List<Review>>{
        return reviewLiveData
    }

    fun initReviews(){
        reviewLiveData.value = reviews
    }

    fun addReview(review: Review){
        reviews.add(review)
        initReviews()
    }


}