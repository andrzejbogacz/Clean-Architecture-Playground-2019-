package com.example.loquicleanarchitecture.view.main.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import com.example.loquicleanarchitecture.helper.observe
import com.example.loquicleanarchitecture.helper.viewModel
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.view.main.SharedViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_random_chats.*
import javax.inject.Inject

class RandomChatsFragment : DaggerFragment(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var picasso: Picasso

    lateinit var imageLoader: ImageLoader

    lateinit var navController: NavController

    lateinit var chatViewModel: RandomChatsViewModel

    lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)


        sharedViewModel = ViewModelProvider(
            activity!!.viewModelStore,
            viewModelFactory
        )[SharedViewModel::class.java]

        navController = NavHostFragment.findNavController(this)
        chatViewModel = viewModel(viewModelFactory) {
            observe(getNextUserLiveData(), ::startChat)
        }

        return inflater.inflate(R.layout.fragment_random_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }

        floatingActionButton.setOnClickListener {
            findUser()
        }

        initAdapter()
    }

    fun startChat(pair: Pair<*, *>) {
        //todo pass user as args
        val bundle: Bundle = Bundle().apply { putSerializable("userAndPhotos", pair) }

        //todo 1.prepareChatToolbar(photos, userDetails)

        navController.navigate(R.id.action_register_to_registered)
    }


    fun findUser() {

        chatViewModel.queryUsers(sharedViewModel.getUserDetailsLiveData().value!!)
    }

    private fun initAdapter() {
        dialogsAdapter = DialogsListAdapter(imageLoader)
        //dialogsAdapter.setItems(DialogsFixtures.dialogs)

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



