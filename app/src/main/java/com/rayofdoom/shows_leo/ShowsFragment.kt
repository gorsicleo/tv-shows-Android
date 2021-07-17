package com.rayofdoom.shows_leo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayofdoom.shows_leo.databinding.FragmentShowsBinding
import com.rayofdoom.shows_leo.utility_functions.fillShowsData

class ShowsFragment: Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    private val shows = fillShowsData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.noShowsLayout.visibility = View.INVISIBLE
        binding.showsRecycler.visibility = View.VISIBLE


        initRecyclerView()
        binding.clearButton.setOnClickListener {
            Toast.makeText(requireActivity().applicationContext, "Show list cleared", Toast.LENGTH_SHORT).show()
            binding.showsRecycler.visibility = View.INVISIBLE
            binding.noShowsLayout.visibility = View.VISIBLE
        }
        binding.backButton.apply {

        }

        binding.backButton.setOnClickListener {
            //startActivity(LoginActivity.buildIntent(this))
        }
        binding.root.setOnClickListener {
            //val action = FirstFragmentDirections.actionFirstToSecond(9)
            //findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(requireActivity().applicationContext, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = ShowsAdapter(shows) { show ->
//            intent = ShowDetailsActivity.buildIntent(
//                username = intent.getStringExtra(ShowsActivity.EXTRA_USERNAME).toString(),
//                activity = this,
//                showTitle = show.showTitle,
//                showDescription = show.showDescription,
//                showImage = show.imageResource
//            )
//            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}