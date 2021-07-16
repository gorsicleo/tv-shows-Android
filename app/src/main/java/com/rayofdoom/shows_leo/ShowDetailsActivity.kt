package com.rayofdoom.shows_leo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayofdoom.shows_leo.databinding.ActivityShowDetailsBinding
import com.rayofdoom.shows_leo.model.Review

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowDetailsBinding

    private val reviews = listOf(
        Review("imenko.prezimenovic", "super je", R.drawable.ic_profile_placeholder, 4.9),
        Review("brane", "bezveze", R.drawable.ic_profile_placeholder, 2.3),
        Review(
            "Leo",
            "Zapevati pred punom Kombank arenom je životna želja svakog pevača. Ja sam, zahvaljujući vama, svoju životnu želju ispunio 08. marta 2016. godine. Sve moje najlepše pesme posvećene su  damama, pa je nekako logičan korak bio da im posvetim i ceo jedan koncert.",
            R.drawable.ic_profile_placeholder,
            5.0
        )
    )

    companion object {

        private const val EXTRA_SHOW_TITLE = "EXTRA_SHOW_TITLE"
        private const val EXTRA_SHOW_DESCRIPTION = "EXTRA_SHOW_DESCRIPTION"
        private const val EXTRA_SHOW_IMAGE_ID = "EXTRA_SHOW_IMAGE_ID"


        fun buildIntent(
            activity: Activity,
            showTitle: String,
            @StringRes showDescription: Int,
            @DrawableRes showImage: Int
        ): Intent {
            val intent = Intent(activity, ShowDetailsActivity::class.java)
            intent.putExtra(EXTRA_SHOW_TITLE, showTitle)
            intent.putExtra(EXTRA_SHOW_DESCRIPTION, showDescription)
            intent.putExtra(EXTRA_SHOW_IMAGE_ID, showImage)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            showDetailsDescription.setText(intent.getIntExtra(EXTRA_SHOW_DESCRIPTION, -1))
            showDetailsImage.setImageResource(
                intent.getIntExtra(
                    EXTRA_SHOW_IMAGE_ID,
                    R.drawable.kt2
                )
            )
            collapsingToolbar.title = intent.getStringExtra(EXTRA_SHOW_TITLE)
        }






        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.reviewsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.reviewsRecycler.adapter = ItemReviewAdapter(reviews)
    }
}