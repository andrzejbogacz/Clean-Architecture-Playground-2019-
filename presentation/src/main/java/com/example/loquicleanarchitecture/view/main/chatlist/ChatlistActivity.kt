package com.example.loquicleanarchitecture.view.main.chatlist

import android.os.Bundle
import com.example.loquicleanarchitecture.BaseActivity
import com.example.loquicleanarchitecture.view.chat.ChatActivity
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.utils.AppUtils
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import org.jetbrains.anko.startActivity
import javax.inject.Inject

abstract class ChatlistActivity : BaseActivity(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {


    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var picasso: Picasso

    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader =
            ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }
    }


    override fun onDialogLongClick(dialog: Dialog) {
        AppUtils.showToast(
            this,
            dialog.dialogName,
            false
        )
    }

    override fun onDialogClick(dialog: Dialog?) {
        startActivity<ChatActivity>()
    }


}