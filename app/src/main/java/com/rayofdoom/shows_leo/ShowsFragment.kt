package com.rayofdoom.shows_leo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Camera
import android.hardware.camera2.CameraDevice
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rayofdoom.shows_leo.databinding.DialogAddReviewBinding
import com.rayofdoom.shows_leo.databinding.DialogUserPanelBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowDetailsBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowsBinding
import com.rayofdoom.shows_leo.model.Review
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.FileUtil
import com.rayofdoom.shows_leo.utility_functions.fillShowsData
import com.rayofdoom.shows_leo.utility_functions.preparePermissionsContract
import java.io.File
import java.io.IOException
import java.util.jar.Manifest

private const val LOGIN_PASSED_FLAG = "passedLogin"
private const val USERNAME = "username"
private const val REQUEST_IMAGE_CAPTURE = 1

class ShowsFragment : Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!
    private val args: ShowsFragmentArgs by navArgs()


    private val cameraPermissionForPhoto = preparePermissionsContract(onPermissionsGranted = {
        takePicture()
    })

    private val cameraContract = ActivityResultContracts.TakePicture()

    private val viewModel: ShowsViewModel by viewModels()

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
        binding.noShowsLayout.isVisible = false
        binding.showsRecycler.isVisible = true
        viewModel.initShows()
        viewModel.getShowsLiveData().observe(this.viewLifecycleOwner,{ shows ->
            initRecyclerView(shows)
        })

        binding.clearButton.setOnClickListener {
            Toast.makeText(context, "Show list cleared", Toast.LENGTH_SHORT).show()
            binding.showsRecycler.isVisible = false
            binding.noShowsLayout.isVisible = true
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


    private fun initRecyclerView(shows: List<Show>) {

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




    private fun takePicture() {
                    val photoFile: File? = try {
                        FileUtil.createImageFile(requireContext())
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {photo ->
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "${requireContext().applicationContext.packageName}.fileprovider",
                            photo
                        )
                        cameraContract.
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
                    //val image = FileUtil.getImageFile(context)

            }
        }


        dialog.show()

    }
}
