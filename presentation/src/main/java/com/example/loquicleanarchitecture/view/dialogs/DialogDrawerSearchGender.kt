package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.dialog_drawer_gender.view.*


class DialogDrawerSearchGender : DialogFragment() {

    internal lateinit var listener: GenderListener

    interface GenderListener {
        fun applyGender(gender: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_drawer_gender, null)

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            //Todo
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.pl_drawer_dialog_genderTitle)
                .setPositiveButton(R.string.pl_confirm,
                    { dialog, id ->
                        listener.applyGender(getCheckedRadioButton(view))
                    })
                .setNegativeButton(R.string.pl_cancel,
                    { dialog, id ->
                        // User cancelled the dialog
                    })
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
            listener = context as GenderListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

}

fun getCheckedRadioButton(view: View): Int {

    val maleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderMale).id
    val femaleId = view.findViewById<RadioButton>(R.id.radioButton_drawer_dialog_genderFemale).id

    val radioButtonID = view.radioGroup.checkedRadioButtonId

    when (radioButtonID) {
        maleId -> return R.string.pl_drawer_dialog_genderMale
        femaleId -> return R.string.pl_drawer_dialog_genderFemale
        else -> return R.string.pl_drawer_dialog_genderBoth
    }
}

