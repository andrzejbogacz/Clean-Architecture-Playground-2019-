package com.example.loquicleanarchitecture.view.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.domain.entities.Gender
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.databinding.FragmentProfileBinding
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.helper.observe
import com.example.loquicleanarchitecture.helper.viewModel
import com.example.loquicleanarchitecture.view.main.SharedViewModel
import com.makeramen.roundedimageview.RoundedImageView
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.view_profile_user_details.*
import javax.inject.Inject


class ProfileFragment : DaggerFragment(),
    View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    lateinit var sharedViewModel: SharedViewModel

    var currentViewHolder: ImageView? = null

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //loadImage()

        initListeners()
        sharedViewModel = viewModel(viewModelFactory) {
            observe(getUserDetailsLiveData(), ::updateUI)
            observe(getUserPhotosLiveData(), ::updatePhotos)
        }


        binding.sharedViewModel = sharedViewModel


        if (this.tag != null) {
            Log.d("TAG", this.tag)
        } else {
            Log.d("TAG", "NULL")
        }
        binding.sharedViewModel = sharedViewModel

        // TODO Add delete button for photos https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGE_INPUT.MD
    }

    private fun initListeners() {
        //todo DATA BINDING
        constraintLayoutNickname.setOnClickListener(this)
        layout_constraint_profile_gender.setOnClickListener(this)
        constraintLayoutAge.setOnClickListener(this)
        iv_photo1.setOnClickListener(this)
        iv_photo2.setOnClickListener(this)
        iv_photo3.setOnClickListener(this)
        iv_photo4.setOnClickListener(this)
        iv_photo5.setOnClickListener(this)
        iv_photo6.setOnClickListener(this)
    }

    private fun loadImagePicker(v: View) {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
            .start(requireActivity())
        currentViewHolder = v as ImageView
    }

    private fun loadImage(photoLink: String?, containerView: RoundedImageView) {

        photoLink?.run {
            Glide.with(this@ProfileFragment)
                .load(photoLink).transform(CenterCrop(), RoundedCorners(20))
                .placeholder(R.drawable.gif_loading)
                .into(containerView.also { it.setBackgroundColor(Color.TRANSPARENT) })
        }
    }

    private fun updateUI(userEntity: UserEntity) {
        textView_profile_nickname_value.text = userEntity.nickname
        textView_profile_age_value.text = userEntity.age.toString()

        when (userEntity.gender) {
            Gender.FEMALE -> textView_profile_gender_value.text =
                getString(R.string.drawer_dialog_genderFemale)
            Gender.MALE -> textView_profile_gender_value.text =
                getString(R.string.drawer_dialog_genderMale)
        }
    }

    private fun updatePhotos(userPhotos: UserPhotos) {
        loadImage(userPhotos.photo1, iv_photo1)
        loadImage(userPhotos.photo2, iv_photo2)
        loadImage(userPhotos.photo3, iv_photo3)
        loadImage(userPhotos.photo4, iv_photo4)
        loadImage(userPhotos.photo5, iv_photo5)
        loadImage(userPhotos.photo6, iv_photo6)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintLayoutNickname -> Navigation.findNavController(v).navigate(R.id.dialogProfileNicknameChoice)
            R.id.layout_constraint_profile_gender -> Navigation.findNavController(v).navigate(R.id.dialogProfileGenderChoice)
            R.id.constraintLayoutAge -> Navigation.findNavController(v).navigate(R.id.dialogProfileAgeChoice)
            R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6 -> loadImagePicker(
                v
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                setAsLoading()
                Log.d("TAG", currentViewHolder!!.contentDescription.toString())
                //   toast(string)
                val uriAndTag =
                    Pair(resultUri.toString(), currentViewHolder!!.contentDescription.toString())
                sharedViewModel.uploadProfileUserPhoto(uriAndTag)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun setAsLoading() {
        // todo gif not animating
        Glide.with(this)
            .asGif()
            .load("https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif")
            .into(currentViewHolder!!)
    }
}
