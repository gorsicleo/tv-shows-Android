package com.rayofdoom.shows_leo.shows

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.database.ShowsDatabase
import com.rayofdoom.shows_leo.database.entities.ShowEntity
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.model.network_models.response.LoginResponse
import com.rayofdoom.shows_leo.model.network_models.response.ShowsResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

private const val PHOTO_UPLOAD_MESSAGE_SUCCESS = "Photo upload successful"
private const val PHOTO_UPLOAD_MESSAGE_FAIL = "Photo upload failed"

class ShowsViewModel(val database: ShowsDatabase) : ViewModel() {
    private val showsResultLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
    }
    private val userPhotoLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getShows(): LiveData<List<ShowEntity>> {
        return database.showDao().getAllShows()
    }

    fun getUserPhotoLiveData(): LiveData<String> {
        return userPhotoLiveData
    }


    fun fetch(topRated: Boolean = false,context: Context) {
        if (!topRated) {
            ApiModule.retrofit.fetchShows().enqueue(object : Callback<ShowsResponse> {
                override fun onResponse(
                    call: Call<ShowsResponse>,
                    response: Response<ShowsResponse>
                ) {
                    showsResultLiveData.value = response.body()?.shows
                    //update database
                    Executors.newSingleThreadExecutor().execute {
                        database.showDao()
                            .insertAllShows(response.body()?.shows!!.mapToShowsEntityList())
                    }

                }

                override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                    //in case of failure load database data
                    showsResultLiveData.value =
                        database.showDao().getAllShows().value?.mapToShowsList()

                }
            })
        } else {
            ApiModule.retrofit.fetchTopRatedShows().enqueue(object : Callback<ShowsResponse> {
                override fun onResponse(
                    call: Call<ShowsResponse>,
                    response: Response<ShowsResponse>
                ) {
                    showsResultLiveData.value = response.body()?.shows
                }

                override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                    Toast.makeText(context, "You cannot fetch top rated shows in offline mode!", Toast.LENGTH_SHORT).show()

                }
            })
        }

    }


fun uploadUserPhoto(context: Context) {

    if (FileUtil.getImageFile(context) != null) {
        val requestFile = FileUtil.getImageFile(context)!!
            .asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val profilePic = MultipartBody.Part.createFormData(
            "image",
            FileUtil.getImageFile(context)?.name, requestFile
        )
        ApiModule.retrofit.uploadPicture(profilePic).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                userPhotoLiveData.value = response.body()?.user?.imageUrl
                Toast.makeText(context, PHOTO_UPLOAD_MESSAGE_SUCCESS, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, PHOTO_UPLOAD_MESSAGE_FAIL, Toast.LENGTH_SHORT).show()
            }

        })
    }
}

}