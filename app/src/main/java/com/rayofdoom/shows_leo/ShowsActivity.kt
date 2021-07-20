package com.rayofdoom.shows_leo


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayofdoom.shows_leo.databinding.ActivityShowsBinding
import com.rayofdoom.shows_leo.utility_functions.ShowDataResource

class ShowsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowsBinding
    private val shows = ShowDataResource.showData

    companion object {
        private const val EXTRA_USERNAME = "EXTRA_USERNAME"

        fun buildIntent(activity: Activity, username: String): Intent {
            val intent = Intent(activity, ShowsActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, username)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initRecyclerView()
        binding.clearButton.setOnClickListener {
            Toast.makeText(this, "Show list cleared", Toast.LENGTH_SHORT).show()
            binding.apply {
                showsRecycler.visibility = View.INVISIBLE
                noShowsLayout.visibility = View.VISIBLE
            }

        }

        binding.backButton.setOnClickListener {
            startActivity(LoginActivity.buildIntent(this))
        }

    }


    private fun initRecyclerView() {
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = ShowsAdapter(shows) { show ->
            intent = ShowDetailsActivity.buildIntent(
                username = intent.getStringExtra(EXTRA_USERNAME).toString(),
                activity = this,
                showTitle = show.showTitle,
                showDescription = show.showDescription,
                showImage = show.imageResource
            )
            startActivity(intent)
        }
    }
}