package com.rayofdoom.shows_leo.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.network_models.request.RegisterRequest
import com.rayofdoom.shows_leo.model.network_models.response.RegisterResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.parseAPIError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val NO_INTERNET_ERROR_MESSAGE = "Make sure you are connected to internet!"

class RegisterViewModel : ViewModel() {

    private val registerResultLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getRegisterResultLiveData(): LiveData<Boolean> {
        return registerResultLiveData
    }

    fun register(email: String, password: String, passwordConfirmation: String,context: Context) {
        val request = RegisterRequest(email, password, passwordConfirmation)

        ApiModule.retrofit.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (!response.isSuccessful){
                    val message = response.errorBody()?.string()
                    if (message != null) {
                        Toast.makeText(context, message.parseAPIError(), Toast.LENGTH_LONG).show()
                    }
                }
                registerResultLiveData.value = response.isSuccessful
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(context, NO_INTERNET_ERROR_MESSAGE, Toast.LENGTH_LONG)
                    .show()
                registerResultLiveData.value = false
            }

        })
    }
}