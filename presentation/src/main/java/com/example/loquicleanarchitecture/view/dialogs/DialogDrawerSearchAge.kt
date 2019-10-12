package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainActivityViewModel
import com.example.loquicleanarchitecture.view.profile.ProfileViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_drawer_age_range_choice.view.*
import javax.inject.Inject

class DialogDrawerSearchAge : DaggerDialogFragment() {
    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainActivityViewModel

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        profileViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(ProfileViewModel::class.java)

        mainViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(MainActivityViewModel::class.java)

        val userData = mainViewModel.getUserDetailsLiveData().value!!

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
                    profileViewModel.changeUserAgePreference(Pair(firstValue, secondValue))
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