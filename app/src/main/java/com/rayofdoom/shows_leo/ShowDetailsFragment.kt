package com.rayofdoom.shows_leo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rayofdoom.shows_leo.databinding.DialogAddReviewBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowDetailsBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.utility_functions.fillReviewData
import com.rayofdoom.shows_leo.utility_functions.getCumulativeRatingForShow
import com.rayofdoom.shows_leo.utility_functions.round


private const val EMAIL_USERNAME_SEPARATOR = "@"

class ShowDetailsFragment : Fragment() {
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private var reviewsAdapter: ItemReviewAdapter? = null
    private val reviews: MutableList<Review> = fillReviewData()

    private val args: ShowDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            showDetailsDescription.setText(args.showDescriptionResId)
            showDetailsImage.setImageResource(args.showImageResId)

            collapsingToolbar?.title = args.showTitle
            showDetailsTitle?.text = args.showTitle
        }

        binding.backButton?.setOnClickListener {
            ShowDetailsFragmentDirections.actionShowDetailsToShows(args.username,false).also {
                findNavController().navigate(it)
            }
        }

        binding.buttonWriteReview.setOnClickListener {
            showBottomSheet()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.reviewsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        reviewsAdapter = ItemReviewAdapter(reviews)
        displayAverage()
        binding.reviewsRecycler.adapter = reviewsAdapter
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogAddReviewBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.addReviewButton.setOnClickListener {
            Toast.makeText(context, dialogBinding.reviewInput.text, Toast.LENGTH_SHORT)
                .show()
        }

        dialog.show()
        dialogBinding.addReviewButton.setOnClickListener {
            if (dialogBinding.rating.rating.toDouble() == 0.0) {
                Toast.makeText(
                    requireContext(),
                    "You must enter score to submit a review!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                reviews.add(
                    Review(
                        args.username
                            .subSequence(
                                0,
                                args.username
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
            "${showRatingData.numberOfReviews} REVIEWS, ${showRatingData.averageScore.round()} AVERAGE"
        binding.showDetailsRatingBar.rating = showRatingData.averageScore.toFloat()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}