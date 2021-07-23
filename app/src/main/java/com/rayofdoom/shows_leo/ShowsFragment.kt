package com.rayofdoom.shows_leo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rayofdoom.shows_leo.databinding.DialogUserPanelBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowsBinding
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.utility_functions.FileUtil
import com.rayofdoom.shows_leo.utility_functions.displayAvatar
import com.rayofdoom.shows_leo.utility_functions.fillShowsData
import com.rayofdoom.shows_leo.utility_functions.preparePermissionsContract
import java.io.File
import java.io.IOException


private const val LOGIN_PASSED_FLAG = "passedLogin"
private const val USERNAME = "username"

class ShowsFragment : Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!
    private val args: ShowsFragmentArgs by navArgs()
    private val shows = fillShowsData()


    private val cameraPermissionForPhoto = preparePermissionsContract(onPermissionsGranted = {
        takePicture()
    })


    private val cameraContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                val dialogBinding = DialogUserPanelBinding.inflate(layoutInflater)

                dialogBinding.userPanelPhoto.displayAvatar(requireContext())
                binding.logOutButton.displayAvatar(requireContext())
            }

        }


    private val viewModel: ShowsViewModel by viewModels()

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
        viewModel.initShows()
        viewModel.getShowsLiveData().observe(this.viewLifecycleOwner, { shows ->
            initRecyclerView(shows)
        })

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        with(sharedPrefs.edit()) {
            if (args.rememberMeChecked) {
                putBoolean(LOGIN_PASSED_FLAG, true)
                putString(USERNAME, args.username)
                apply()
            }
        }

        initRecyclerView(shows)

        binding.clearSwitch?.setOnClickListener {
            if (binding.clearSwitch!!.isChecked) {
                Toast.makeText(context, getString(R.string.shows_cleared), Toast.LENGTH_SHORT)
                    .show()
                binding.showsRecycler.visibility = View.INVISIBLE
                binding.noShowsLayout.visibility = View.VISIBLE
            } else {
                binding.showsRecycler.visibility = View.VISIBLE
                binding.noShowsLayout.visibility = View.INVISIBLE
            }
        }

        binding.logOutButton.displayAvatar(requireContext())

        binding.logOutButton.setOnClickListener {
            showBottomSheet()
        }

    }


    private fun initRecyclerView(shows: List<Show>) {

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


    private fun takePicture() {
        val photoFile: File? = try {
            FileUtil.createImageFile(requireContext())
        } catch (ex: IOException) {
            // Error occurred while creating the File
            null
        }
        // Continue only if the File was successfully created
        photoFile?.also { photo ->
            context?.apply {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "${this.applicationContext.packageName}.fileprovider",
                    photo
                )
                cameraContract.launch(photoURI)
            }

        }
    }


    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogUserPanelBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.apply {
            userPanelEmail.text = args.username
            val avatarExists = dialogBinding.userPanelPhoto.displayAvatar(requireContext())
            if (!avatarExists){
                userPanelPhoto.setImageResource(R.drawable.ic_profile_placeholder)
            }
            userPanelLogoutButton.setOnClickListener {
                val sharedPrefs =
                    activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
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
                dialog.dismiss()
            }
        }


        dialog.show()

    }
}
