package com.example.loquicleanarchitecture.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.dialog_profile_nickname_choice.*

class DialogProfileAgeChoice : DialogFragment() {

    internal lateinit var listener: AgeListener

    interface AgeListener {
        fun applyAge(age: Int?)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_profile_age_choice, null)

        val np_ageChoice = view.findViewById<NumberPicker>(R.id.numberPicker_age_choice)
        np_ageChoice.minValue = 1
        np_ageChoice.maxValue = 99

        np_ageChoice.value = 18

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.pl_profile_age_title)
                .setPositiveButton(R.string.pl_confirm
                ) { dialog, id ->
                    listener.applyAge(np_ageChoice.value)
                }
                .setNegativeButton(R.string.pl_cancel
                ) { dialog, id ->
                    // User cancelled the dialog
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as AgeListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement AgeListener")
            )
        }
    }

}