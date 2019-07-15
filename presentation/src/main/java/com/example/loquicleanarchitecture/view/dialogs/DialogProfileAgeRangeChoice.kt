package com.example.loquicleanarchitecture.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.dialog_drawer_age_range_choice.*
import kotlinx.android.synthetic.main.dialog_profile_age_choice.*
import kotlinx.android.synthetic.main.dialog_profile_nickname_choice.*
import kotlinx.android.synthetic.main.dialog_profile_nickname_choice.nickname_dialog_cancel
import kotlinx.android.synthetic.main.dialog_profile_nickname_choice.nickname_dialog_confirm

class DialogProfileAgeRangeChoice(var c: Activity)
    : Dialog(c), View.OnClickListener {
    var d: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_profile_age_choice)


        numberPicker_age_choice.maxValue = 99
        numberPicker_age_choice.minValue = 1
        numberPicker_age_choice.value = 18


        nickname_dialog_cancel.setOnClickListener(this)
        nickname_dialog_confirm.setOnClickListener(this)

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