package com.example.loquicleanarchitecture.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.dialog_drawer_age_range_choice.*

class DialogDrawerSearchAgeRange(var c: Activity)
    : Dialog(c), View.OnClickListener {
    var d: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_drawer_age_range_choice)

        drawer_age_range_picker_first.maxValue = 99
        drawer_age_range_picker_first.minValue = 1
        drawer_age_range_picker_first.value = 18

        drawer_age_range_picker_second.maxValue = 99
        drawer_age_range_picker_second.minValue = 1
        drawer_age_range_picker_second.value = 45

        drawer_age_range_dialog_cancel.setOnClickListener(this)
        drawer_age_range_dialog_confirm.setOnClickListener(this)




    }

    override fun onClick(v: View) {
        when (v.id) { //TODO Implementation
            R.id.nickname_dialog_confirm -> c.finish()
            R.id.nickname_dialog_cancel -> dismiss()
            else -> {
            }
        }
        dismiss()
    }
}