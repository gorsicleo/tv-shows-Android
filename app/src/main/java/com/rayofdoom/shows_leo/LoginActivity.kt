package com.rayofdoom.shows_leo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import com.google.android.material.textfield.TextInputLayout
import com.rayofdoom.shows_leo.databinding.ActivityLoginBinding




class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            performCheck()
        }
    }

    fun performCheck(){

    }
}