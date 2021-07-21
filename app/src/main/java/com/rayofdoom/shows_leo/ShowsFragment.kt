package com.rayofdoom.shows_leo

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.MediaStore
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
import com.rayofdoom.shows_leo.databinding.DialogUserPanelBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowDetailsBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowsBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.utility_functions.fillShowsData
import com.rayofdoom.shows_leo.utility_functions.preparePermissionsContract
import java.util.jar.Manifest

private const val LOGIN_PASSED_FLAG = "passedLogin"
private const val USERNAME = "username"

class ShowsFragment : Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!
    private val args: ShowsFragmentArgs by navArgs()

    private val shows = fillShowsData()
    private val cameraPermissionForPhoto = preparePermissionsContract(onPermissionsGranted = {
        dispatchTakePictureIntent()
    })

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
            Toast.makeText(context, "Show list cleared", Toast.LENGTH_SHORT).show()
            binding.showsRecycler.visibility = View.INVISIBLE
            binding.noShowsLayout.visibility = View.VISIBLE
        }

        binding.logOutButton.setOnClickListener {
            showBottomSheet()
        }

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        with(sharedPrefs.edit()) {
            if (args.rememberMeChecked) {
                putBoolean(LOGIN_PASSED_FLAG, true)
                putString(USERNAME, args.username)
                apply()
            }
        }
    }

    private fun isTablet(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    private fun initRecyclerView() {

        binding.showsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = ShowsAdapter(shows) { show ->

            ShowsFragmentDirections.actionShowsToShowsDetails(
                args.username,
                show.showTitle,
                show.showDescription,
                show.imageResource
            ).also {
                findNavController().navigate(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogUserPanelBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.apply {
            userPanelEmail.text=args.username
            userPanelPhoto.setImageResource(R.drawable.ic_profile_placeholder)
            userPanelLogoutButton.setOnClickListener{
                val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
                with(sharedPrefs.edit()) {
                    if (args.rememberMeChecked) {
                        putBoolean(LOGIN_PASSED_FLAG, false)
                        putString(USERNAME, null)
                        apply()
                        dialog.dismiss()
                        findNavController().navigate(ShowsFragmentDirections.actionShowsToLogin())
                    }
                }
            }

            userPanelChangeProfilePhotoButton.setOnClickListener {
                    cameraPermissionForPhoto.launch(arrayOf(android.Manifest.permission.CAMERA))
            }
        }


        dialog.show()

    }
}
