package com.rayofdoom.shows_leo.networking

import android.content.Context
import com.rayofdoom.shows_leo.utility_functions.PrefsUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val ACCESS_TOKEN = "access-token"
private const val TOKEN_TYPE = "token-type"
private const val CLIENT = "client"
private const val UID = "uid"



class HeaderInterceptor(private val headers: List<String?>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        //add headers only if they are known
        if (headers[0]!=null && headers[1]!=null && headers[2]!=null){
            request = request.newBuilder()
                .header(TOKEN_TYPE, "Bearer")
                .header(ACCESS_TOKEN, headers[0]!!)
                .header(CLIENT, headers[1]!!)
                .header(UID, headers[2]!!)
                .build()
        }
        return chain.proceed(request)
    }

}