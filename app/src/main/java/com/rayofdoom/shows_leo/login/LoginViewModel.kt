package com.rayofdoom.shows_leo.login

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.databinding.FragmentLoginBinding
import com.rayofdoom.shows_leo.model.User
import com.rayofdoom.shows_leo.model.network_models.request.LoginRequest
import com.rayofdoom.shows_leo.model.network_models.response.LoginResponse
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.parseAPIError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val INVALID_CREDENTIALS_ERROR_MESSAGE = "Invalid login credentials. Please try again."
private const val ACCESS_TOKEN = "access-token"
private const val CLIENT = "client"
private const val UID = "uid"

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

    fun login(email: String, password: String, context: Context,binding: FragmentLoginBinding) {
        val request = LoginRequest(email, password)

        ApiModule.retrofit.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.headers()[ACCESS_TOKEN] != null &&
                        response.headers()[CLIENT] != null &&
                        response.headers()[UID] != null
                    ) {
                        headers = listOf(
                            response.headers()[ACCESS_TOKEN]!!,
                            response.headers()[CLIENT]!!,
                            response.headers()[UID]!!
                        )
                        user = response.body()!!.user
                    }
                } else {
                    val message = response.errorBody()?.string()
                    if (message != null) {
                        if (message.parseAPIError() == INVALID_CREDENTIALS_ERROR_MESSAGE)
                        binding.errorMessage.text = context.getString(R.string.invalid_credentials_error)
                        binding.errorMessage.visibility = View.VISIBLE
                    }
                }

                loginResultLiveData.value = response.isSuccessful

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                binding.errorMessage.text = context.getString(R.string.network_error)
                binding.errorMessage.visibility = View.VISIBLE
                loginResultLiveData.value = false
            }

        })
    }
}