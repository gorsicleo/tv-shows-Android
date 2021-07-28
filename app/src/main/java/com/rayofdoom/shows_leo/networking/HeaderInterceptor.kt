package com.rayofdoom.shows_leo.networking

import okhttp3.Interceptor
import okhttp3.Response

private const val ACCESS_TOKEN = "access-token"
private const val TOKEN_TYPE = "token-type"
private const val CLIENT = "client"
private const val UID = "uid"



class HeaderInterceptor(val headers: List<String?>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader(ACCESS_TOKEN, "Bearer")
                    .addHeader(TOKEN_TYPE, headers[0])
                    .addHeader(CLIENT, headers[1])
                    .addHeader(UID, headers[2])
                    .build()
            )
        }
    }