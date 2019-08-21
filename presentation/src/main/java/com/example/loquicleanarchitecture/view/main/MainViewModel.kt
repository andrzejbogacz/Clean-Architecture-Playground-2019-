package com.example.loquicleanarchitecture.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import arrow.core.Failure
import arrow.core.None
import com.example.data.usecases.ChangeUserAgePreference
import com.example.data.usecases.CreateUser
import com.example.data.usecases.LoadUser
import com.example.domain.entities.UserEntity
import com.example.domain.exception.FirebaseResult
import com.example.domain.exception.FirebaseResult.ExistingUserLoaded
import com.example.domain.exception.FirebaseResult.NewUserCreated
import com.example.domain.exception.UserFirebaseException
import com.example.loquicleanarchitecture.view.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class MainViewModel @Inject constructor(
    val createUser: CreateUser, val loadUser: LoadUser, val changeUserAgePreference: ChangeUserAgePreference
) : BaseViewModel() {

    init {
        listenToUserChanges()
    }

    private val TAG: String? = this.javaClass.name

    private val userData: MutableLiveData<UserEntity> = MutableLiveData()

    private fun createUser() = createUser(None) { it.fold(::handleFailure, ::handleSuccess) }
    fun loadUser() = loadUser(None) { it.fold(::handleFailure, ::handleSuccess) }
    fun changeUserAgePreference(pair: Pair<Int, Int>) =
        changeUserAgePreference(pair) { it.fold(::handleFailure, ::handleSuccess) }

    fun handleFailure(e: Failure) {
        Log.d(TAG, "Failed Loading user with Exception: ${e.exception.javaClass.simpleName}")
        when (e.exception) {
            is UserFirebaseException.UserNotExisting -> {
                Log.d(TAG, "Starting attempt to create new User")
                createUser()
            }

            //todo catch and define new exception handlers
            is UserFirebaseException.UnknownException -> Log.d(
                TAG,
                " Unhandled exception within FirebaseRepository: check logs"
            )
        }
    }

    private fun listenToUserChanges() {
        val userDocument =
            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
        userDocument.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                // throwable = e
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                userData.value = snapshot.toObject(UserEntity::class.java)!!

            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    private fun handleSuccess(s: Any?) {
        when (s) {
            is NewUserCreated -> Log.d(TAG, "handleSuccess: Successfully saved new user to remote database")
            is ExistingUserLoaded -> Log.d(TAG, "handleSuccess: Successfully loaded existing user")
            is FirebaseResult.UserAgePreferencesChanged -> Log.d(
                TAG,
                "handleSuccess: Successfully changed user age preference"
            )
        }
    }

    fun getUserDataLiveData(): MutableLiveData<UserEntity> {
        return userData
    }
}