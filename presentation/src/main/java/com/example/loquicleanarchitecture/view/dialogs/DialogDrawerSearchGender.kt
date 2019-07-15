package com.example.loquicleanarchitecture.view.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.dialog_drawer_gender.*

class DialogDrawerSearchGender(var c: Activity)
    : Dialog(c), View.OnClickListener {
    var d: Dialog? = null
    lateinit var yes: TextView
    lateinit var no: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_drawer_gender)

        nickname_dialog_cancel.setOnClickListener(this)
        nickname_dialog_confirm.setOnClickListener(this)



    }

    override fun onClick(v: View) {
        when (v.id) { //TODO Implementation //TODO Implementation
            R.id.nickname_dialog_confirm -> c.finish()
            R.id.nickname_dialog_cancel -> dismiss()
            else -> {
            }
        }
        dismiss()
    }
}