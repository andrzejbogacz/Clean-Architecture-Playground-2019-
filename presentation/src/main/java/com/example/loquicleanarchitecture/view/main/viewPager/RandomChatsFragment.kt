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
import com.example.data.model.Dialog
import com.example.loquicleanarchitecture.view.main.MainActivityViewModel
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.fragment_random_chats.*
import javax.inject.Inject

class RandomChatsFragment : ChatManager(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    @Inject
    lateinit var picasso: Picasso

    lateinit var imageLoader: ImageLoader

    lateinit var navController: NavController

    lateinit var randomChatsViewModel: RandomChatsViewModel

    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            super.onCreate(savedInstanceState)


        mainActivityViewModel = ViewModelProvider(activity!!.viewModelStore, viewModelFactory)[MainActivityViewModel::class.java]

        navController = NavHostFragment.findNavController(this)
        randomChatsViewModel = viewModel(viewModelFactory) {
            observe(getNextUserLiveData(), ::startChat)
        }

        return inflater.inflate(R.layout.fragment_random_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageLoader = ImageLoader { imageView, url, payload -> picasso.load(url).into(imageView) }

        floatingActionButton.setOnClickListener {
           findUser()
            mainActivityViewModel.createDialogs()
        }

        initAdapter()
    }

    fun findUser() {

        randomChatsViewModel.queryUsers(mainActivityViewModel.getUserDetailsLiveData().value!!)
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
        //todo DIALOG info, co skad dlaczego czy sie przyda i czy cos zmieniac

        findNavController().navigate(R.id.chatroomFragment)
    }
}



