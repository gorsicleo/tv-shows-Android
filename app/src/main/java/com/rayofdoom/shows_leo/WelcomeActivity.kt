package com.rayofdoom.shows_leo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayofdoom.shows_leo.databinding.ActivityWelcomeBinding

private const val EMAIL_USERNAME_SEPARATOR = "@"

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayWelcomeMessage()
    }

    private fun displayWelcomeMessage() {
        val welcomeMessage = intent.getStringExtra("user").toString()
            .subSequence(
                0,
                intent.getStringExtra("user").toString().indexOf(EMAIL_USERNAME_SEPARATOR)
            )

        binding.welcomeMessage.text = String.format("Welcome, %s", welcomeMessage)
    }
}