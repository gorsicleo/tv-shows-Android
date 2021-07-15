package com.rayofdoom.shows_leo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayofdoom.shows_leo.databinding.ActivityWelcomeBinding

private const val EMAIL_USERNAME_SEPARATOR = "@"

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    companion object {

        private const val EXTRA_USERNAME = "EXTRA_USERNAME"

        fun buildIntent(activity: Activity, username: String): Intent {
            val intent = Intent(activity, WelcomeActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, username)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayWelcomeMessage()

        Thread{
            makeIntent()
        }.start()
    }

    private fun displayWelcomeMessage() {
        val welcomeMessage = intent.getStringExtra(EXTRA_USERNAME).toString()
            .subSequence(
                0,
                intent.getStringExtra(EXTRA_USERNAME).toString().indexOf(EMAIL_USERNAME_SEPARATOR)
            )

        binding.welcomeMessage.text = String.format("Welcome, %s", welcomeMessage)
    }
    private fun makeIntent() {
        //this is used to shortly display welcome message to the user before making intent
        Thread.sleep(1500)
        startActivity(Intent(this, ShowsActivity::class.java))
    }
}