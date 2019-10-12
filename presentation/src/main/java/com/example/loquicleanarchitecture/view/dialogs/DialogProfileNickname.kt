package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainActivityViewModel
import com.example.loquicleanarchitecture.view.profile.ProfileViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class DialogProfileNickname : DaggerDialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var sharedViewModel: MainActivityViewModel

    private lateinit var profileViewModel: ProfileViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        profileViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(ProfileViewModel::class.java)

        sharedViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(MainActivityViewModel::class.java)

        val userNickname = sharedViewModel.getUserDetailsLiveData().value!!.nickname

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_nickname_choice, null)
        val edNickname = view.findViewById<EditText>(R.id.editText_profile_nickname_value)

        edNickname.setText(userNickname, TextView.BufferType.EDITABLE)
        edNickname.setSelection(edNickname.text.length)

        return activity?.let {

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.profile_dialog_nickname_title)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    profileViewModel.changeProfileUserNickname(edNickname.text.toString())

                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    // User cancelled the dialog
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}