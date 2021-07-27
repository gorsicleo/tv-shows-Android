package com.rayofdoom.shows_leo.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.model.User
import com.rayofdoom.shows_leo.model.network_models.request.LoginRequest
import com.rayofdoom.shows_leo.model.network_models.response.LoginResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val loginResultLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    private var headers = emptyList<String>()
    private var user: User? = null

    fun getLoginResultLiveData(): LiveData<Boolean> {
        return loginResultLiveData
    }

    fun getHeaders(): List<String> {
        return headers
    }

    fun getUser(): User? {
        return user
    }

    fun login(email: String, password: String) {
        val request = LoginRequest(email, password)

        ApiModule.retrofit.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                headers = listOf(
                    response.headers()["access-token"]!!,
                    response.headers()["client"]!!,
                    response.headers()["uid"]!!
                )
                user = response.body()!!.user
                loginResultLiveData.value = response.isSuccessful

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResultLiveData.value = false
            }

        })
    }
}