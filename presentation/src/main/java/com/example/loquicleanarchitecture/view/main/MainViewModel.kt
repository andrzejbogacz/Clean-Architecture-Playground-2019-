package com.example.loquicleanarchitecture.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import arrow.core.Failure
import arrow.core.None
import com.example.data.FirebaseRepository
import com.example.data.usecases.CreateUser
import com.example.data.usecases.LoadUser
import com.example.domain.entities.UserEntity
import com.example.domain.exception.UserFirebaseException
import com.example.loquicleanarchitecture.view.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val createUser: CreateUser, val loadUser: LoadUser, val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    private val TAG: String? = this.javaClass.name
    var userData: MutableLiveData<UserEntity> = MutableLiveData()

    fun createUser() = createUser(None) { it.fold(::handleFailure, ::handleSuccess) }
    fun loadUser() = loadUser(None) { it.fold(::handleFailure, ::handleSuccess) }


    fun handleFailure(e: Failure) {
        Log.d(TAG, "Failed Loading user with Exception: ${e.exception.javaClass.simpleName}")
        when (e.exception) {
            is UserFirebaseException.UserNotExisting -> {
                Log.d(TAG, "Starting attempt to create new User")
                createUser()
            }
            is UserFirebaseException.UnknownException -> {
                Log.d(TAG, "Unknown Exception: ${e.exception.printStackTrace()}")
            }
        }
    }

    fun handleSuccess(s: UserEntity?) {
        Log.d(TAG, "handleSuccess : User Loaded Successfully")
        userData.value = s
    }
}


