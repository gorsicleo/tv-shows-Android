package com.rayofdoom.shows_leo.shows

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rayofdoom.shows_leo.R
import com.rayofdoom.shows_leo.ShowsApp
import com.rayofdoom.shows_leo.databinding.DialogUserPanelBinding
import com.rayofdoom.shows_leo.databinding.FragmentShowsBinding
import com.rayofdoom.shows_leo.model.Show
import com.rayofdoom.shows_leo.networking.ApiModule
import com.rayofdoom.shows_leo.utility_functions.*
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

private const val LOGIN_PASSED_FLAG = "passedLogin"
private const val USERNAME = "username"


class ShowsFragment : Fragment() {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!
    private val args: ShowsFragmentArgs by navArgs()
    private val viewModel: ShowsViewModel by viewModels {
        ShowViewModelFactory((requireActivity().application as ShowsApp).showsDatabase)
    }


    private val cameraPermissionForPhoto = preparePermissionsContract(onPermissionsGranted = {
        takePicture()
    })

    private val cameraContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                DialogUserPanelBinding.inflate(layoutInflater)
                //update views with new avatar
                viewModel.uploadUserPhoto(requireContext())
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalSerializationApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPrefs.edit()) {
            if (args.rememberMeChecked) {
                putBoolean(LOGIN_PASSED_FLAG, true)
                putString(USERNAME, args.username)
                apply()
            }
        }


        viewModel.fetch()
        viewModel.getShows().observe(this.viewLifecycleOwner, { shows ->
            if (shows != null) {
                initRecyclerView(shows.map {
                    Show(
                        it.showId,
                        it.showTitle,
                        it.showDescription,
                        it.imageResource,
                        it.averageRating,
                        it.noOfReviews
                    )
                })
                if (shows.isEmpty()){
                    clearShows(true)
                }
            } else {
                clearShows(true)
            }
        })
        viewModel.getUserPhotoLiveData().observe(this.viewLifecycleOwner, { url ->
            PrefsUtil.updateUserImageUrl(requireActivity(), url)
            binding.logOutButton.displayAvatar(requireContext(), url)
        })


        binding.logOutButton.apply {
            displayAvatar(
                requireContext(),
                PrefsUtil.loadUserFromPrefs(requireActivity()).imageUrl
            )
            setOnClickListener {
                showBottomSheet()
            }
        }

    }


    private fun initRecyclerView(shows: List<Show>) {
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = ShowsAdapter(shows, requireContext()) { show ->

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

    private fun clearShows(value: Boolean) {
        if (value) {
            Toast.makeText(context, getString(R.string.shows_cleared), Toast.LENGTH_SHORT)
                .show()
        }
        binding.showsRecycler.isInvisible = value
        binding.noShowsLayout.isVisible = value
    }


    private fun takePicture() {
        val photoFile: File? = FileUtil.createImageFile(requireContext())

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


    @ExperimentalSerializationApi
    private fun logout() {
        //initialize retrofit -> forget headers
        ApiModule.initRetrofit(listOf(null, null, null))
        val sharedPrefs =
            activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPrefs.edit()) {
            putBoolean(LOGIN_PASSED_FLAG, false)
            putString(USERNAME, null)
            apply()
            val action = ShowsFragmentDirections.actionShowsToLogin()
            action.registerSuccess = false
            findNavController().navigate(action)
        }
    }


    @ExperimentalSerializationApi
    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val dialogBinding = DialogUserPanelBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.apply {
            userPanelEmail.text = args.username
            val avatarUrl = PrefsUtil.loadUserFromPrefs(requireActivity()).imageUrl
            dialogBinding.userPanelPhoto.displayAvatar(requireContext(), avatarUrl)
            userPanelLogoutButton.setOnClickListener {
                dialog.dismiss()
                logout()
            }
            userPanelChangeProfilePhotoButton.setOnClickListener {
                cameraPermissionForPhoto.launch(arrayOf(android.Manifest.permission.CAMERA))
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}