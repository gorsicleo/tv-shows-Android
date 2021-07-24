package com.rayofdoom.shows_leo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rayofdoom.shows_leo.databinding.DialogAddReviewBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowDetailsBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.getCumulativeRatingForShow
import com.rayofdoom.shows_leo.utility_functions.parseUsernameFromEmail

class ShowDetailsFragment : Fragment() {
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private var reviewsAdapter: ItemReviewAdapter? = null

    private val args: ShowDetailsFragmentArgs by navArgs()
    private val viewModelShows: ShowsViewModel by viewModels()
    private val viewModelShowDetails: ShowDetailsViewModel by viewModels()

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
        startViewModels()
        binding.apply {
            backButton?.setOnClickListener {
                ShowDetailsFragmentDirections.actionShowDetailsToShows(args.username, false).also {
                    findNavController().navigate(it)
                }
            }
            clearSwitch?.apply {
                setOnClickListener {
                    viewModelShowDetails.loadDummyReviews(isChecked)
                }
            }
            buttonWriteReview.setOnClickListener {
                showBottomSheet()
            }
        }
    }


    private fun startViewModels() {
        viewModelShows.apply {
            initShows()
            getShowsLiveData().observe(viewLifecycleOwner, { shows ->
                displayShowDetails(shows[args.showId])
            })
        }
        viewModelShowDetails.apply {
            initReviews()
            getReviewsLiveData().observe(viewLifecycleOwner, { reviews ->
                initRecyclerView(reviews)
            })
        }
    }

    private fun displayShowDetails(show: Show) {
        binding.apply {
            showDetailsDescription.setText(show.showDescription)
            showDetailsImage.setImageResource(show.imageResource)
            collapsingToolbar?.title = show.showTitle
            showDetailsTitle?.text = show.showTitle
        }
    }

    private fun initRecyclerView(reviews: List<Review>) {
        binding.reviewsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        reviewsAdapter =
            ItemReviewAdapter(reviews, args.username.parseUsernameFromEmail(), requireContext())
        displayAverage(reviews)
        binding.reviewsRecycler.adapter = reviewsAdapter
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialog)
        val dialogBinding = DialogAddReviewBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        dialogBinding.addReviewButton.setOnClickListener {
            if (dialogBinding.rating.rating.toInt() == 0) {
                Toast.makeText(
                    context,
                    getString(R.string.toast_message_no_score),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModelShowDetails.addReview(
                    Review(
                        args.username.parseUsernameFromEmail(),
                        dialogBinding.reviewInput.text.toString(),
                        R.drawable.ic_profile_placeholder,
                        dialogBinding.rating.rating.toInt()
                    )
                )
                dialog.dismiss()
            }

        }
    }

    private fun displayAverage(reviews: List<Review>) {
        val showRatingData = reviews.getCumulativeRatingForShow()
        binding.showDetailsReviewData.text =
            getString(
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