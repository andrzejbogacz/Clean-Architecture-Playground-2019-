package com.example.loquicleanarchitecture.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.helper.ImageTransformation
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileAgeChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileGenderChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileNicknameChoice
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.view_profile_user_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileActivity : AppCompatActivity(),
    View.OnClickListener,
    DialogProfileNicknameChoice.NicknameListener,
    DialogProfileAgeChoice.AgeListener,
    DialogProfileGenderChoice.ProfileGenderListener {

    var currentViewHolder: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initListeners()

        // TODO Add delete button for photos https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGE_INPUT.MD
        // Todo Add save button on toolbar
    }

    private fun initListeners() {
        constraintLayoutNickname.setOnClickListener(this)
        constraintLayoutGender.setOnClickListener(this)
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
            .start(this);
        currentViewHolder = v as ImageView
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintLayoutNickname -> DialogProfileNicknameChoice().show(supportFragmentManager, "nickname")
            R.id.constraintLayoutGender -> DialogProfileGenderChoice().show(supportFragmentManager, "gender")
            R.id.constraintLayoutAge -> DialogProfileAgeChoice().show(supportFragmentManager, "age")
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
                setPhoto(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    override fun applyGender(gender: Int?) {
        gender?.run {
            textView_profile_gender_value.setText(gender)
        }
    }

    override fun applyAge(age: Int?) {
        textView_profile_age_value.text = age.toString()
    }


    override fun applyNickname(nickname: String) {
        if (nickname.isNotBlank())
            textView_profile_nickname_value.text = nickname
    }

    private fun uploadImages(path: Uri) {
        GlobalScope.launch {
            //Todo Upload Images to Firebase

        }

    }



    private fun setPhoto(resultUri: Uri?) {
        Picasso.get()
            .load(resultUri)
            .transform(ImageTransformation.transformation)
            .into(currentViewHolder)
    }


}
