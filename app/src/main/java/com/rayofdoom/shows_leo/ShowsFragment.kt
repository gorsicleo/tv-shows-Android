package com.rayofdoom.shows_leo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rayofdoom.shows_leo.databinding.FragmentShowsBinding
import com.rayofdoom.shows_leo.utility_functions.fillShowsData

class ShowsFragment : Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!
    private val args: ShowsFragmentArgs by navArgs()

    private val shows = fillShowsData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initRecyclerView()
        binding.clearSwitch?.let { switch ->
            switch.setOnClickListener {
                if (switch.isChecked) {
                    Toast.makeText(context, getString(R.string.shows_cleared), Toast.LENGTH_SHORT)
                        .show()
                }
                binding.showsRecycler.isInvisible = switch.isChecked.not()
                binding.noShowsLayout.isVisible = switch.isChecked

            }
        }

        binding.logOutButton.setOnClickListener {
            ShowsFragmentDirections.actionShowsToLogin().also {
                findNavController().navigate(it)
            }
        }
    }


    private fun initRecyclerView() {

        binding.showsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = ShowsAdapter(shows) { show ->

            ShowsFragmentDirections.actionShowsToShowsDetails(
                args.username,
                show.showId
            ).also {
                findNavController().navigate(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
