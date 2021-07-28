package com.rayofdoom.shows_leo.show_details

import android.content.Context
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
import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.databinding.DialogAddReviewBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowDetailsBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.displayShowImage

private const val ACCESS_TOKEN = "access-token"
private const val CLIENT = "client"
private const val UID = "uid"
private const val BASE_URL = "https://tv-shows.infinum.academy/shows/"

class ShowDetailsFragment : Fragment() {
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private var reviewsAdapter: ItemReviewAdapter? = null
    private var headers: List<String?> = emptyList<String>()

    private val args: ShowDetailsFragmentArgs by navArgs()
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

        loadHeadersFromPrefs()

        viewModelShowDetails.fetchShowDetails(BASE_URL + args.showId.toString())
        viewModelShowDetails.fetchReviews(BASE_URL + args.showId.toString() + "/reviews")
        startViewModels()
        setClickListeners()

    }

    private fun setClickListeners() {
        binding.apply {
            backButton?.setOnClickListener {
                ShowDetailsFragmentDirections.actionShowDetailsToShows(
                    args.username,
                    false
                ).also {
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

    private fun loadHeadersFromPrefs() {
        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        headers = listOf(
            sharedPrefs.getString(ACCESS_TOKEN, null),
            sharedPrefs.getString(CLIENT, null),
            sharedPrefs.getString(
                UID, null
            )
        )
    }


    private fun startViewModels() {

        viewModelShowDetails.apply {
            initReviews()
            getReviewsLiveData().observe(viewLifecycleOwner, { reviews ->
                if (reviews!=null) {
                    initRecyclerView(reviews)
                }
            })
            getShowDetailsLiveData().observe(viewLifecycleOwner, { show ->
                if (show!=null) {
                    displayShowDetails(show)
                }
            })
        }
    }

    private fun displayShowDetails(show: Show) {
        binding.apply {
            showDetailsDescription.text = show.showDescription
            show.imageResource?.let { showDetailsImage.displayShowImage(requireContext(), it) }
            collapsingToolbar?.title = show.showTitle
            showDetailsTitle?.text = show.showTitle
            displayAverage(show)
        }
    }

    private fun initRecyclerView(reviews: List<Review>) {
        binding.reviewsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        reviewsAdapter =
            ItemReviewAdapter(reviews, requireContext())
        binding.reviewsRecycler.adapter = reviewsAdapter
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
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
                    dialogBinding.rating.rating.toInt(),
                    dialogBinding.reviewInput.text.toString(),
                    args.showId
                )
                dialog.dismiss()

            }

        }


    }


    private fun displayAverage(show: Show) {
        binding.showDetailsReviewData.text =
            getString(
                R.string.display_average_format,
                show.noOfReviews,
                show.averageRating
            )
        binding.showDetailsRatingBar.rating = show.averageRating?.toFloat() ?: 0.0f
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}