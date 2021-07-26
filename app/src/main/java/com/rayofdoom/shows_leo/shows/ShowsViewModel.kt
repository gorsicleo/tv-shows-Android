package com.rayofdoom.shows_leo.shows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.network_models.response.ShowsResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.fillShowsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsViewModel : ViewModel() {
    private val showsResultLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
    }

    fun getShowsLiveData(): LiveData<List<Show>> {
        return showsResultLiveData
    }


    fun fetch(headers: List<String?>) {


        ApiModule.retrofit.fetchShows(
            tokenType = "Bearer",
            accessToken = headers[0],
            client = headers[1],
            uid = headers[2]
        ).enqueue(object : Callback<ShowsResponse> {
            override fun onResponse(
                call: Call<ShowsResponse>,
                response: Response<ShowsResponse>
            ) {
                showsResultLiveData.value = response.body()?.shows
            }

            override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                showsResultLiveData.value = fillShowsData()
            }


        })
    }

}

