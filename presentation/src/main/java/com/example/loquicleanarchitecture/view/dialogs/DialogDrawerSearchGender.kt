package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entities.Gender
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.view.main.MainViewModel
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.dialog_drawer_gender.view.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject


class DialogDrawerSearchGender : DaggerDialogFragment() {
    private val TAG: String? = this.javaClass.name

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_drawer_gender, null)

        mainViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(MainViewModel::class.java)
        var genderPreference = mainViewModel.getUserDataLiveData().value!!.preferences_gender

        toast(genderPreference.name)

        setCheckedRadioButton(genderPreference , view)

        return activity?.let {

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.pl_drawer_dialog_genderTitle)
                .setPositiveButton(R.string.pl_confirm
                ) { dialog, id ->
                    genderPreference = getSelectedGender(view)
                }
                .setNegativeButton(
                    R.string.pl_cancel
                ) { _, _ ->
                    // User cancelled the dialog
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

fun getSelectedGender(view: View): Gender {
    val maleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderMale).id
    val femaleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderFemale).id
    val radioButtonID = view.radioGroup.checkedRadioButtonId

    return when (radioButtonID) {
        maleId -> Gender.MALE
        femaleId -> Gender.FEMALE
        else -> Gender.BOTH
    }
}

fun setCheckedRadioButton(genderPreference: Gender, view: View) {
    val maleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderMale)
    val femaleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderFemale)
    val bothId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderBoth)
    val radioButtonID = view.radioGroup
    radioButtonID.clearCheck()

    with(genderPreference.name)
    {
        when {
            equals("FEMALE") -> {femaleId.isChecked = true ;  Log.d("MainActivity", "FEMALE")}
            equals("MALE") -> {maleId.isChecked = true ;  Log.d("MainActivity", "MALE")}
            else -> {bothId.isChecked = true ;  Log.d("MainActivity", "BOTH")}
        }
    }
}