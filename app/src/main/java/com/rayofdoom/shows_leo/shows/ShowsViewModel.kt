package com.rayofdoom.shows_leo.shows

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.network_models.response.LoginResponse
import com.rayofdoom.shows_leo.model.network_models.response.ShowsResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.FileUtil
import com.rayofdoom.shows_leo.utility_functions.PrefsUtil
import com.rayofdoom.shows_leo.utility_functions.fillShowsData
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsViewModel : ViewModel() {
    private val showsResultLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
    }
    private val userPhotoLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getShowsLiveData(): LiveData<List<Show>> {
        return showsResultLiveData
    }

    fun getUserPhotoLiveData(): LiveData<String> {
        return userPhotoLiveData
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

    fun uploadUserPhoto(headers: List<String?>, context: Context) {

        if (FileUtil.getImageFile(context) != null) {
            val requestFile = FileUtil.getImageFile(context)!!
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val profilePic = MultipartBody.Part.createFormData(
                "image",
                FileUtil.getImageFile(context)?.name, requestFile
            )
            ApiModule.retrofit.uploadPicture(
                "Bearer",
                headers[0],
                headers[1],
                headers[2],
                profilePic
            ).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    userPhotoLiveData.value = response.body()?.user?.imageUrl
                    Toast.makeText(context, "Photo upload successful", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context, "Photo upload failed", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

}