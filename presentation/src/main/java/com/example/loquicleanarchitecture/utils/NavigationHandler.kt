package com.example.loquicleanarchitecture.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import com.example.loquicleanarchitecture.R

class NavigationHandler {

    @BindingAdapter("view")
    fun onNavigate(view: View, parameter: Boolean) {
        val tag = view.contentDescription.toString()
        when (tag) {
            "layoutNickname" -> Navigation.findNavController(view).navigate(R.id.dialogProfileNicknameChoice)
            "layoutGender" -> Navigation.findNavController(view).navigate(R.id.dialogProfileGenderChoice)
            "layoutAge" -> Navigation.findNavController(view).navigate(R.id.dialogProfileAgeChoice)
        }
    }
}