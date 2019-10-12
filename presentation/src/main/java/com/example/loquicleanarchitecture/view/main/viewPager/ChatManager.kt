package com.example.loquicleanarchitecture.view.main.viewPager

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.loquicleanarchitecture.R
import dagger.android.support.DaggerFragment

open class ChatManager
    : DaggerFragment() {
    private val TAG: String? = this.javaClass.name


    fun startChat(pair: Pair<*, *>) {
        //todo pass user as args
        val bundle: Bundle = Bundle().apply { putSerializable("userAndPhotos", pair) }

        //todo 1.prepareChatToolbar(photos, userDetails)

        findNavController().navigate(R.id.action_register_to_registered)
    }

}



