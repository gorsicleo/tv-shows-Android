package com.rayofdoom.shows_leo

import android.app.Fragment
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
import com.rayofdoom.shows_leo.utility_functions.getShowById
import com.rayofdoom.shows_leo.utility_functions.parseUsernameFromEmail


class ShowDetailsFragment : Fragment() {
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private var reviewsAdapter: ItemReviewAdapter? = null
    private var reviews: MutableList<Review> = emptyList<Review>().toMutableList()

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
        val show = getShowById(args.showId)
        binding.apply {
            showDetailsDescription.setText(show.showDescription)
            showDetailsImage.setImageResource(show.imageResource)

            collapsingToolbar?.title = show.showTitle
            showDetailsTitle?.text = show.showTitle
            buttonWriteReview.setOnClickListener {
                showBottomSheet()
            }
            backButton?.setOnClickListener {
                ShowDetailsFragmentDirections.actionShowDetailsToShows(args.username).also {
                    findNavController().navigate(it)
                }
            }

            clearSwitch?.setOnClickListener {
                if (clearSwitch.isChecked) {
                    reviews = fillReviewData()
                    initRecyclerView()
                } else {
                    reviews.clear()
                    initRecyclerView()
                }
            }

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
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialog)
        val dialogBinding = DialogAddReviewBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)


        dialog.show()
        dialogBinding.addReviewButton.setOnClickListener {
            if (dialogBinding.rating.rating.toDouble() == 0.0) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.toast_message_no_score),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                reviews.add(
                    Review(
                        args.username.parseUsernameFromEmail(),
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
        binding.showDetailsReviewData.text = getString(
            R.string.display_average_format,
            showRatingData.numberOfReviews,
            showRatingData.averageScore
        )
        binding.showDetailsRatingBar.rating = showRatingData.averageScore.toFloat()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}