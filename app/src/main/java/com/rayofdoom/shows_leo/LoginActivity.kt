package com.rayofdoom.shows_leo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.rayofdoom.shows_leo.databinding.ActivityLoginBinding
import com.rayofdoom.shows_leo.util_functions.textListenersInit


class LoginActivity : AppCompatActivity() {
    var conditionEmail = false
    var conditionPass = false

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //this is used to disable button when app starts
        binding.loginButton.isEnabled = false
        textListenersInit()
    }


}