package com.example.loquicleanarchitecture.view.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.databinding.FragmentProfileBinding
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.helper.viewModel
import com.example.loquicleanarchitecture.utils.NavigationHandler
import com.example.loquicleanarchitecture.view.main.MainActivityViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileFragment @Inject constructor(
) : DaggerFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    lateinit var sharedViewModel: MainActivityViewModel

    private var currentViewHolder: ImageView? = null

    private lateinit var binding: FragmentProfileBinding

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        sharedViewModel = viewModel(viewModelFactory) {}

        profileViewModel = viewModel(viewModelFactory) {}

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(MainActivityViewModel::class.java)

        binding.include.navigationHandler = NavigationHandler()

        binding.sharedViewModel = sharedViewModel
        binding.include.sharedViewModel = sharedViewModel

        binding.profileFragment = this
        binding.lifecycleOwner = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri

                val uriAndTag =
                    Pair(resultUri.toString(), currentViewHolder!!.contentDescription.toString())

                profileViewModel.uploadProfileUserPhoto(uriAndTag)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    fun loadImagePicker(v: View) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
            .start(requireActivity())
        currentViewHolder = v as ImageView
    }

    fun deletePhoto(view: View) =
        profileViewModel.deleteProfileUserPhoto(view.contentDescription.toString())
}