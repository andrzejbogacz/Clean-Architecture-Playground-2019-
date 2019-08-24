package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.loquicleanarchitecture.R

class DialogProfileAgeChoice : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_age_choice, null)

        val np_ageChoice = view.findViewById<NumberPicker>(R.id.numberPicker_age_choice)
        np_ageChoice.minValue = 1
        np_ageChoice.maxValue = 99

        np_ageChoice.value = 18

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.profile_age_title)
                .setPositiveButton(R.string.confirm
                ) { dialog, id ->
                    // todo
                }
                .setNegativeButton(R.string.cancel
                ) { dialog, id ->
                    // User cancelled the dialog
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}