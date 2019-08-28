package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entities.GenderPreference
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_drawer_gender.view.*
import javax.inject.Inject


class DialogDrawerSearchGender : DaggerDialogFragment() {
    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_drawer_gender, null)

        mainViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        ).get(MainViewModel::class.java)
//        val genderPreference = mainViewModel.getUserDataLiveData().value!!.preferences_gender

        //  toast(genderPreference.name)

        // setCheckedRadioButton(genderPreference, view)

        return activity?.let {

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.drawer_dialog_genderTitle)
                .setPositiveButton(
                    R.string.confirm
                ) { dialog, id ->
                    mainViewModel.changeUserGenderPreference(getSelectedGender(view))
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

fun getSelectedGender(view: View): GenderPreference {
    val maleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderMale).id
    val femaleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderFemale).id
    val radioButtonID = view.radioGroup.checkedRadioButtonId

    return when (radioButtonID) {
        maleId -> GenderPreference.MALE
        femaleId -> GenderPreference.FEMALE
        else -> GenderPreference.BOTH
    }
}

fun setCheckedRadioButton(genderPreference: GenderPreference, view: View) {
    val maleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderMale)
    val femaleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderFemale)
    val bothId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderBoth)
    val radioButtonID = view.radioGroup
    radioButtonID.clearCheck()

    with(genderPreference.name)
    {
        when {
            equals("FEMALE") -> {
                femaleId.isChecked = true; Log.d("MainActivity", "FEMALE")
            }
            equals("MALE") -> {
                maleId.isChecked = true; Log.d("MainActivity", "MALE")
            }
            else -> {
                bothId.isChecked = true; Log.d("MainActivity", "BOTH")
            }
        }
    }
}