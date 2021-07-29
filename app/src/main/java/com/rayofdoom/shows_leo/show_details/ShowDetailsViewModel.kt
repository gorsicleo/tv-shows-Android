package com.rayofdoom.shows_leo.show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.database.ShowsDatabase
import com.rayofdoom.shows_leo.database.entities.ShowEntity
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.network_models.request.ReviewRequest
import com.rayofdoom.shows_leo.model.network_models.response.*
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class ShowDetailsViewModel(val database: ShowsDatabase) : ViewModel() {

    private var reviews: MutableList<Review> = mutableListOf()
    private val reviewLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    private val showDetailsLiveData: MutableLiveData<Show> by lazy {
        MutableLiveData<Show>()
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

    fun addReview(rating: Int, comment: String, showId: Int) {
        val request = ReviewRequest(rating, comment, showId)

        ApiModule.retrofit.createReview(request).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                response.body()?.review?.let { reviews.add(0, it) }
                initReviews()
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                reviewLiveData.value = fillReviewData()
            }
        })

    }


    fun getShowDetails(showId: String): LiveData<ShowEntity> {
        return database.showDao().getShow(showId)
    }

    fun fetchShowDetails(endpoint: String,showId: String) {

        ApiModule.retrofit.fetchShow(endpoint).enqueue(object : Callback<ShowDetailsResponse> {
            override fun onResponse(
                call: Call<ShowDetailsResponse>,
                response: Response<ShowDetailsResponse>
            ) {
                showDetailsLiveData.value = response.body()?.show
            }

            override fun onFailure(call: Call<ShowDetailsResponse>, t: Throwable) {
                //in case of failure load database data
                showDetailsLiveData.value = database.showDao().getShow(showId).value?.mapToShow()
            }

        })

    }


    fun fetchReviews(endpoint: String,showId: String) {

        ApiModule.retrofit.fetchReviews(endpoint).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                reviewLiveData.value = response.body()?.reviews
                Executors.newSingleThreadExecutor().execute {
                    database.reviewDao().insertAllReviews(response.body()?.reviews!!.mapToReviewEntityList())
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                //in case of failure load database data
                reviewLiveData.value = database.reviewDao().getReviews(showId).value?.mapToReviewsList()
            }

        })

    }

}