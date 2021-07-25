package com.rayofdoom.shows_leo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.RegisterRequest
import com.rayofdoom.shows_leo.model.RegisterResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val registerResultLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getRegisterResultLiveData(): LiveData<Boolean> {
        return registerResultLiveData
    }

    fun register(email: String, password: String, passwordConfirmation: String) {
        val request = RegisterRequest(email, password, passwordConfirmation)

        ApiModule.retrofit.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                registerResultLiveData.value = response.isSuccessful
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResultLiveData.value = false
            }

        })
    }
}