package com.example.loquicleanarchitecture.view.main.viewPager

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.example.loquicleanarchitecture.R
import dagger.android.support.DaggerFragment

open class ChatManager
    : DaggerFragment() {
    private val TAG: String? = this.javaClass.name

    fun startChat(pair: Pair<UserEntity,UserPhotos>) {

        Log.d(TAG, pair.first.toString())
        Log.d(TAG, pair.second.toString())
        //todo pass user as args
        val bundle: Bundle = Bundle().apply { putSerializable("userAndPhotos", pair) }
        //todo not finished
        findNavController().navigate(R.id.action_chatlistFragment_to_chatroomFragment)
    }

}



