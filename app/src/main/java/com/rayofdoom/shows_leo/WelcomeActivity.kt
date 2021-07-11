package com.rayofdoom.shows_leo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.rayofdoom.shows_leo.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        val bundleget = bundle?.get("user")
        val welcomeStr = bundleget.toString().subSequence(0,bundleget.toString().indexOf("@"))
        binding.welcomeMsg.text = "Welcome, "+welcomeStr


    }
}