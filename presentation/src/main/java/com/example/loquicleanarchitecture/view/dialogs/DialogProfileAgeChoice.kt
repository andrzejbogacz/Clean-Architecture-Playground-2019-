package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.SharedViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class DialogProfileAgeChoice : DaggerDialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        sharedViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(SharedViewModel::class.java)
        val userAge = sharedViewModel.getUserDataLiveData().value!!.age

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_age_choice, null)

        val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker_age_choice)

        numberPicker.minValue = 1
        numberPicker.maxValue = 99

        numberPicker.value = userAge

        return activity?.let {

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.profile_age_title)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    sharedViewModel.changeProfileUserAge(numberPicker.value)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    // User cancelled the dialog
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}