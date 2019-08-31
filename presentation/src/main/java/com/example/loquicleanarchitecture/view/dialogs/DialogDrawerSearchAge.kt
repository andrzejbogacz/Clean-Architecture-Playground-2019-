package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.SharedViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_drawer_age_range_choice.view.*
import javax.inject.Inject

class DialogDrawerSearchAge : DaggerDialogFragment() {
    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: SharedViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(SharedViewModel::class.java)

        val userData = mainViewModel.getUserDataLiveData().value!!

        var firstValue: Int = userData.preferences_age_range_min
        var secondValue: Int = userData.preferences_age_range_max

        Log.d(TAG, userData.toString())
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_drawer_age_range_choice, null)

        with(view) {

            with(drawer_age_range_picker_first) {
                maxValue = 99
                minValue = 1
                value = firstValue
                setOnValueChangedListener { _, _, newVal -> firstValue = newVal }
            }
            with(drawer_age_range_picker_second) {
                maxValue = 99
                minValue = 1
                value = secondValue
                setOnValueChangedListener { _, _, newVal -> secondValue = newVal }
            }
        }

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.drawer_dialog_ageRangeTitle)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    mainViewModel.changeUserAgePreference(Pair(firstValue, secondValue))
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