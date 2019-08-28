package com.example.loquicleanarchitecture.view.main.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.utils.AppUtils
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
//        goProfile.setOnClickListener {
//             Navigation.findNavController(it).navigate(R.id.action_chatlistFragment_to_profileFragmentNav)
//
//        }
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
            context!!.applicationContext,
            dialog.dialogName,
            false
        )
    }

    override fun onDialogClick(dialog: Dialog?) {
        //  startActivity<ChatActivity>()
    }
}



