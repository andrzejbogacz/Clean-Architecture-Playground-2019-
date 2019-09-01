package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.databinding.DialogProfileNicknameChoiceBinding
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.SharedViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class DialogProfileNickname : DaggerDialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: SharedViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(SharedViewModel::class.java)

        val userNickname = mainViewModel.getUserDataLiveData().value!!.nickname

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_nickname_choice, null)
        val edNickname = view.findViewById<EditText>(R.id.editText_profile_nickname_value)

        val dialogBinding: DialogProfileNicknameChoiceBinding =
            DialogProfileNicknameChoiceBinding.inflate(LayoutInflater.from(context))

        //edNickname.setText(userNickname, TextView.BufferType.EDITABLE)
        edNickname.setSelection(edNickname.text.length)

        return activity?.let {

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