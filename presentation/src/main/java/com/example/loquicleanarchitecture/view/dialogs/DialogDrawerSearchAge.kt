package com.example.loquicleanarchitecture.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.dialog_drawer_age_range_choice.view.*
import org.jetbrains.anko.support.v4.toast


class DialogDrawerSearchAge : DialogFragment() {

    internal lateinit var listener: AgeRangeListener

    interface AgeRangeListener {
        fun applyAgeRange(ageRange: String)
    }

    var firstValue: Int = 18
    var secondValue: Int = 45

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        toast("onCreateDIalog")

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_drawer_age_range_choice, null)

        with(view) {
            drawer_age_range_picker_first.maxValue = 99
            drawer_age_range_picker_first.minValue = 1
            drawer_age_range_picker_first.value = 18

            drawer_age_range_picker_second.maxValue = 99
            drawer_age_range_picker_second.minValue = 1
            drawer_age_range_picker_second.value = 45

            drawer_age_range_picker_first.setOnValueChangedListener { _, _, newVal -> firstValue = newVal }
            drawer_age_range_picker_second.setOnValueChangedListener { _, _, newVal -> secondValue = newVal }
        }

        return activity?.let {
            // Use the Builder class for convenient dialog construction

            //Todo
            val builder = AlertDialog.Builder(it).setView(view)
            builder.setTitle(R.string.pl_drawer_dialog_ageRangeTitle)
                .setPositiveButton(
                    R.string.pl_confirm
                ) { dialog, id ->
                    listener.applyAgeRange("$firstValue - $secondValue")
                }
                .setNegativeButton(
                    R.string.pl_cancel
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
            listener = context as AgeRangeListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }
}