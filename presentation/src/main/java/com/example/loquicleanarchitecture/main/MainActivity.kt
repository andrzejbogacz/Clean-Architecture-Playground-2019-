package com.example.loquicleanarchitecture.main

import android.os.Bundle
import android.view.Menu
import com.example.loquicleanarchitecture.BaseActivity
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.utils.AppUtils
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {


    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var picasso: Picasso

    lateinit var imageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        imageLoader =
            ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }
        initAdapter()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer, menu)
        return super.onPrepareOptionsMenu(menu)

    }


    private fun initAdapter() {
        dialogsAdapter = DialogsListAdapter(imageLoader)
        dialogsAdapter.setItems(DialogsFixtures.dialogs)

        dialogsAdapter.setOnDialogClickListener(this)
        dialogsAdapter.setOnDialogLongClickListener(this)

        dialogsList.setAdapter(dialogsAdapter)
    }

    override fun onDialogLongClick(dialog: Dialog) {
        AppUtils.showToast(
            this,
            dialog.dialogName,
            false
        )
    }

    override fun onDialogClick(dialog: Dialog?) {
        AppUtils.showToast(
            this,
            dialog!!.dialogName,
            false
        )
    }

}
