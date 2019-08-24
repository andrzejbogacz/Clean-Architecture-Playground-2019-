package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entities.Gender
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_profile_gender_choice.view.*
import javax.inject.Inject

class DialogProfileGenderChoice : DaggerDialogFragment() {

    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        mainViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(MainViewModel::class.java)
        val gender = mainViewModel.getUserDataLiveData().value!!.gender

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_gender_choice, null)

        setCheckedRadioButton(gender, view)

        return activity?.let {
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.profile_iam_title)
                .setPositiveButton(
                    R.string.confirm
                ) { _, _ ->
                    mainViewModel.changeProfileUserGender(getCheckedRadioButton(view))
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

    fun getCheckedRadioButton(view: View): Gender {

        val maleId = view.findViewById<RadioButton>(R.id.radioButton_profile_dialog_genderMale)

        return when (maleId.isChecked) {
            true -> Gender.MALE
            false -> Gender.FEMALE
        }
    }
}

fun setCheckedRadioButton(gender: Gender, view: View) {
    val maleId = view.findViewById<RadioButton>(R.id.radioButton_profile_dialog_genderMale)
    val femaleId = view.findViewById<RadioButton>(R.id.radioButton_profile_dialog_genderFemale)
    val radioButtonID = view.radioGroupProfile
    radioButtonID.clearCheck()

    with(gender.name)
    {
        when {
            equals("FEMALE") -> {
                femaleId.isChecked = true
            }
            equals("MALE") -> {
                maleId.isChecked = true
            }
        }
    }
}