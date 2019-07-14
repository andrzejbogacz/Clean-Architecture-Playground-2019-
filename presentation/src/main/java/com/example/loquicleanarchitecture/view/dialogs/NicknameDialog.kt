package com.example.loquicleanarchitecture.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.drawer_age_range_dialog.*
import kotlinx.android.synthetic.main.drawer_gender_dialog.view.*
import kotlinx.android.synthetic.main.profile_nickname_dialog.*

class NicknameDialog(var c: Activity)
    : Dialog(c), View.OnClickListener {
    var d: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.profile_nickname_dialog)


        nickname_dialog_cancel.setOnClickListener(this)
        nickname_dialog_confirm.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.nickname_dialog_confirm -> c.finish()
            R.id.nickname_dialog_cancel -> dismiss()
            else -> {
            }
        }
        dismiss()
    }
}