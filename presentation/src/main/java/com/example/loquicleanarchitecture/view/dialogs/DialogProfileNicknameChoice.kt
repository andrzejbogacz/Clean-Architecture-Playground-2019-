package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class DialogProfileNicknameChoice : DaggerDialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(MainViewModel::class.java)

        val userData = mainViewModel.getUserDataLiveData().value!!

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_nickname_choice, null)
        val edNickname = view.findViewById<EditText>(R.id.editText_profile_nickname_value)

        edNickname.setText(userData.nickname, TextView.BufferType.EDITABLE)

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            //Todo
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.profile_dialog_nickname_title)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    mainViewModel.changeProfileUserNickname(edNickname.text.toString())

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