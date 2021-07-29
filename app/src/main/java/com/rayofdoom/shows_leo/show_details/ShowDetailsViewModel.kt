package com.rayofdoom.shows_leo.show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.database.ShowsDatabase
import com.rayofdoom.shows_leo.database.entities.ReviewEntity
import com.rayofdoom.shows_leo.database.entities.ShowEntity
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.User
import com.rayofdoom.shows_leo.model.network_models.request.ReviewRequest
import com.rayofdoom.shows_leo.model.network_models.response.*
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class ShowDetailsViewModel(val database: ShowsDatabase) : ViewModel() {

    private val reviewLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    private val showDetailsLiveData: MutableLiveData<Show> by lazy {
        MutableLiveData<Show>()
    }

    fun getReviewsLiveData(showId: String): LiveData<List<ReviewEntity>> {
        return database.reviewDao().getReviewsByShowId(showId)
    }


    fun getShowDetailsLiveData(showId: String): LiveData<ShowEntity> {
        return database.showDao().getShow(showId)
    }

    fun getUnUploadedReviewsLiveData(showId: String): LiveData<List<ReviewEntity>> {
        return database.reviewDao().getAllUnuploadedReviews(showId)
    }

    fun fetchShowDetails(endpoint: String, showId: String) {

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


    fun fetchReviews(endpoint: String, showId: String) {

        ApiModule.retrofit.fetchReviews(endpoint).enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(
                call: Call<ReviewsResponse>,
                response: Response<ReviewsResponse>
            ) {
                reviewLiveData.value = response.body()?.reviews
                Executors.newSingleThreadExecutor().execute {
                    database.reviewDao()
                        .insertAllReviews(response.body()?.reviews!!.mapToReviewEntityList())
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                //in case of failure load database data
                reviewLiveData.value =
                    database.reviewDao().getReviewsByShowId(showId).value?.mapToReviewsList()
                reviewLiveData.value = fillReviewData()
            }
        })
    }


    fun addReview(rating: Int, comment: String, showId: Int, user: User) {
        val request = ReviewRequest(rating, comment, showId)

        ApiModule.retrofit.createReview(request).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.body() != null) {
                    Executors.newSingleThreadExecutor().execute {
                        database.reviewDao()
                            .insertReview(response.body()!!.review.mapToReviewEntity())
                    }
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                putToOfflineQueue(request, showId, user)
            }
        })
    }

    private fun putToOfflineQueue(request: ReviewRequest, showId: Int, user: User) {
        val reviewId = IdGenerator.generateReviewId()

        Executors.newSingleThreadExecutor().execute {
            val reviewToUpload =
                ReviewEntity(reviewId, request.comment, request.rating, showId, user)
            Executors.newSingleThreadExecutor().execute {
                database.reviewDao().insertReview(reviewToUpload)
            }
        }
    }


    fun tryToUpload(unUploaded: List<ReviewEntity>) {

        unUploaded.forEach {
            val request = ReviewRequest(it.rating, it.comment, it.showId)

            ApiModule.retrofit.createReview(request).enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.body() != null) {
                        Executors.newSingleThreadExecutor().execute {
                            database.reviewDao().deleteReview(it)
                            database.reviewDao()
                                .insertReview(response.body()!!.review.mapToReviewEntity())

                        }
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                }
            })
        }
    }


}