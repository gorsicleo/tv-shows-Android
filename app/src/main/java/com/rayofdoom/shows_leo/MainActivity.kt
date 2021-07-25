package com.rayofdoom.shows_leo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayofdoom.shows_leo.networking.ApiModule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiModule.initRetrofit(getPreferences(Context.MODE_PRIVATE))
    }
}