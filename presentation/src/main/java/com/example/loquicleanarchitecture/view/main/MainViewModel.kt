package com.example.loquicleanarchitecture.view.main

import android.util.Log
import arrow.core.None
import arrow.core.extensions.either.foldable.fold
import arrow.core.fix
import com.example.data.usecases.CreateUser
import com.example.loquicleanarchitecture.view.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(val createUser: CreateUser) : BaseViewModel() {

    fun createUser() = createUser(params = None ) { it.fold(
        ::handleFailure,
        ::handleSuccess
    )}

    private fun handleSuccess(s: String) {
        Log.d("TESTING", "success $s")
    }


}