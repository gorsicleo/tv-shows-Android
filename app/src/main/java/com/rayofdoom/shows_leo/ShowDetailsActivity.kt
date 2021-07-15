package com.rayofdoom.shows_leo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rayofdoom.shows_leo.databinding.ActivityLoginBinding
import com.rayofdoom.shows_leo.databinding.ActivityShowDetailsBinding
import com.rayofdoom.shows_leo.databinding.ActivityShowsBinding

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowDetailsBinding

    companion object {

        private const val EXTRA_SHOW_TITLE = "EXTRA_SHOW_TITLE"

        fun buildIntent(activity: Activity, showTitle: String): Intent {
            val intent = Intent(activity, ShowDetailsActivity::class.java)
            intent.putExtra(EXTRA_SHOW_TITLE,showTitle)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}