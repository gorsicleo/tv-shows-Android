package com.rayofdoom.shows_leo.show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.network_models.request.ReviewRequest
import com.rayofdoom.shows_leo.model.network_models.response.*
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.fillReviewData
import com.rayofdoom.shows_leo.utility_functions.fillShowsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsViewModel : ViewModel() {

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

    fun addReview(headers: List<String?>, rating: Int, comment: String, showId: Int) {
        val request = ReviewRequest(rating, comment, showId)

        ApiModule.retrofit.createReview(
            tokenType = "Bearer",
            accessToken = headers[0],
            client = headers[1],
            uid = headers[2], request
        ).enqueue(object : Callback<ReviewResponse> {
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


    fun getShowDetailsLiveData(): LiveData<Show> {
        return showDetailsLiveData
    }

    fun fetchShowDetails(headers: List<String?>, endpoint: String) {

        ApiModule.retrofit.fetchShow(
            tokenType = "Bearer",
            accessToken = headers[0],
            client = headers[1],
            uid = headers[2],
            endpoint
        ).enqueue(object : Callback<ShowDetailsResponse> {
            override fun onResponse(
                call: Call<ShowDetailsResponse>,
                response: Response<ShowDetailsResponse>
            ) {
                showDetailsLiveData.value = response.body()?.show
            }

            override fun onFailure(call: Call<ShowDetailsResponse>, t: Throwable) {
                //in case of failure load dummy data
                showDetailsLiveData.value = fillShowsData()[0]
            }

        })

    }


    fun fetchReviews(headers: List<String?>, endpoint: String) {

        ApiModule.retrofit.fetchReviews(
            tokenType = "Bearer",
            accessToken = headers[0],
            client = headers[1],
            uid = headers[2],
            endpoint
        ).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                response.body()?.reviews?.let { reviews.addAll(it) }
                reviewLiveData.value = reviews
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                //in case of failure load dummy data
                reviewLiveData.value = fillReviewData()
            }

        })

    }

}