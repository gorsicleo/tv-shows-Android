package com.rayofdoom.shows_leo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rayofdoom.shows_leo.databinding.ActivityShowDetailsBinding
import com.rayofdoom.shows_leo.databinding.DialogAddReviewBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.utility_functions.ReviewDataResource
import com.rayofdoom.shows_leo.utility_functions.getCumulativeRatingForShow

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowDetailsBinding
    private var reviewsAdapter: ItemReviewAdapter? = null
    private val reviews: MutableList<Review> = ReviewDataResource.reviewData


    companion object {

        private const val EXTRA_SHOW_TITLE = "EXTRA_SHOW_TITLE"
        private const val EXTRA_SHOW_DESCRIPTION = "EXTRA_SHOW_DESCRIPTION"
        private const val EXTRA_SHOW_IMAGE_ID = "EXTRA_SHOW_IMAGE_ID"
        private const val EXTRA_USERNAME = "EXTRA_USERNAME"
        private const val EMAIL_USERNAME_SEPARATOR = "@"


        fun buildIntent(
            username: String,
            activity: Activity,
            showTitle: String,
            @StringRes showDescription: Int,
            @DrawableRes showImage: Int
        ): Intent {
            val intent = Intent(activity, ShowDetailsActivity::class.java)
            intent.putExtra(EXTRA_SHOW_TITLE, showTitle)
            intent.putExtra(EXTRA_SHOW_DESCRIPTION, showDescription)
            intent.putExtra(EXTRA_SHOW_IMAGE_ID, showImage)
            intent.putExtra(EXTRA_USERNAME, username)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.reviewsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reviewsAdapter = ItemReviewAdapter(reviews)
        displayAverage()
        binding.reviewsRecycler.adapter = reviewsAdapter
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val dialogBinding = DialogAddReviewBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.addReviewButton.setOnClickListener {
            Toast.makeText(this, dialogBinding.reviewInput.text, Toast.LENGTH_SHORT).show()
        }

        dialog.show()
        dialogBinding.addReviewButton.setOnClickListener {
            if (dialogBinding.rating.rating.toDouble() == 0.0) {
                Toast.makeText(this, getString(R.string.toast_message_no_score), Toast.LENGTH_SHORT)
                    .show()
            } else {
                reviews.add(
                    Review(
                        intent.getStringExtra(EXTRA_USERNAME).toString()
                            .subSequence(
                                0,
                                intent.getStringExtra(EXTRA_USERNAME).toString()
                                    .indexOf(EMAIL_USERNAME_SEPARATOR)
                            ).toString(),
                        dialogBinding.reviewInput.text.toString(),
                        R.drawable.ic_profile_placeholder,
                        dialogBinding.rating.rating.toInt()
                    )
                )
                reviewsAdapter?.addItem(reviews)
                displayAverage()
                dialog.dismiss()
            }

        }
    }

    private fun displayAverage() {
        val showRatingData = reviews.getCumulativeRatingForShow()
        binding.showDetailsReviewData.text =
            getString(
                R.string.display_average_format,
                showRatingData.numberOfReviews,
                showRatingData.averageScore
            )
        binding.showDetailsRatingBar.rating = showRatingData.averageScore.toFloat()
    }

    private fun initView() {
        binding.apply {
            showDetailsDescription.setText(intent.getIntExtra(EXTRA_SHOW_DESCRIPTION, -1))
            showDetailsImage.setImageResource(
                intent.getIntExtra(
                    EXTRA_SHOW_IMAGE_ID,
                    R.drawable.kt2
                )
            )
            collapsingToolbar.title = intent.getStringExtra(EXTRA_SHOW_TITLE)

            backButton.setOnClickListener {
                startActivity(
                    ShowsActivity.buildIntent(
                        this@ShowDetailsActivity,
                        intent.getStringExtra(EXTRA_USERNAME).toString()
                    )
                )
            }

            buttonWriteReview.setOnClickListener {
                showBottomSheet()
            }
        }
    }


}