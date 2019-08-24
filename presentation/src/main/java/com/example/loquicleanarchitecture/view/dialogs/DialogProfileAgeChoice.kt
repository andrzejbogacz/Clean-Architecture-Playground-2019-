package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class DialogProfileAgeChoice : DaggerDialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(MainViewModel::class.java)
        val userAge = mainViewModel.getUserDataLiveData().value!!.age

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
                    mainViewModel.changeProfileUserAge(numberPicker.value)
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