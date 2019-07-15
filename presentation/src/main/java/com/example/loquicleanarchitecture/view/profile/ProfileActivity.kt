package com.example.loquicleanarchitecture.view.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionManager
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileAgeRangeChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileGenderChoice
import com.example.loquicleanarchitecture.view.dialogs.DialogProfileNicknameChoice
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.view_profile_user_details.*
import org.jetbrains.anko.toast

class ProfileActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        swapView(image1)

        //TODO Pick and upload photos
        // TODO Add delete button for photos https://github.com/stfalcon-studio/ChatKit/blob/master/docs/COMPONENT_MESSAGE_INPUT.MD
        // Todo Add save button on toolbar
        initListeners()
    }


    fun swapView(v: View) {
        TransitionManager.beginDelayedTransition(layout)
        placeholder.setContentId(v.id)
        toast(v.id)
    }

    private fun initListeners() {
        constraintLayoutNickname.setOnClickListener(this)
        constraintLayoutGender.setOnClickListener(this)
        constraintLayoutAge.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintLayoutNickname -> DialogProfileNicknameChoice(this).show()
            R.id.constraintLayoutGender -> DialogProfileGenderChoice(this).show()
            R.id.constraintLayoutAge -> DialogProfileAgeRangeChoice(this).show()
        }

    }
}
