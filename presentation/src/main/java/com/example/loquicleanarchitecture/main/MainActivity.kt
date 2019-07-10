package com.example.loquicleanarchitecture.main

import android.os.Bundle
import android.view.Menu
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.chat.ChatActivity
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.main.chatlist.ChatlistActivity
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.utils.AppUtils
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : ChatlistActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()

    }

    private fun initAdapter() {
     super.dialogsAdapter = DialogsListAdapter(super.imageLoader)
     super.dialogsAdapter.setItems(DialogsFixtures.dialogs)

     super.dialogsAdapter.setOnDialogClickListener(this)
     super.dialogsAdapter.setOnDialogLongClickListener(this)

        dialogsList.setAdapter(super.dialogsAdapter)
    }
}
