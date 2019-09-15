package com.example.loquicleanarchitecture.view.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.navigation.fragment.findNavController
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.model.Dialog
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_chatlist.*
import javax.inject.Inject

class ChatlistFragment : DaggerFragment(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var picasso: Picasso

    lateinit var imageLoader: ImageLoader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        return inflater.inflate(R.layout.fragment_chatlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }
        initAdapter()
    }

    private fun initAdapter() {
        dialogsAdapter = DialogsListAdapter(imageLoader)
        dialogsAdapter.setItems(DialogsFixtures.dialogs)

        dialogsAdapter.setOnDialogClickListener(this)
        dialogsAdapter.setOnDialogLongClickListener(this)

        dialogsList.setAdapter(dialogsAdapter)
    }

    override fun onDialogLongClick(dialog: Dialog) {
        makeText(
            context!!.applicationContext,
            dialog.dialogName,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDialogClick(dialog: Dialog?) {
        findNavController().navigate(R.id.chatroomFragment)
    }
}



