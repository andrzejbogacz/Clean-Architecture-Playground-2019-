package com.example.loquicleanarchitecture.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import arrow.core.Failure
import arrow.core.None
import com.example.data.FirebaseRepositoryImpl
import com.example.data.usecases.CreateUser
import com.example.data.usecases.LoadUser
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.example.domain.exception.FirebaseResult.ExistingUserLoaded
import com.example.domain.exception.FirebaseResult.NewUserCreated
import com.example.domain.exception.UserFirebaseException
import com.example.loquicleanarchitecture.fixtures.DialogsFixtures
import com.example.loquicleanarchitecture.model.Dialog
import com.example.loquicleanarchitecture.view.BaseViewModel
import javax.inject.Inject
import kotlin.random.Random

class MainActivityViewModel @Inject constructor(
    val createUser: CreateUser,
    val loadUser: LoadUser,
    private var firebaseRepository: FirebaseRepositoryImpl
) : BaseViewModel() {

    private val TAG: String? = this.javaClass.name

    private val userDetailsLiveData: MutableLiveData<UserEntity> = MutableLiveData()
    private val userPhotosLiveData: MutableLiveData<UserPhotos> = MutableLiveData()
    private val userFriendDialogsLiveData: MutableLiveData<Dialog> = MutableLiveData()

    private fun createUser() = createUser(None) { it.fold(::handleFailure, ::handleSuccess) }

    fun loadUser() = loadUser(None) { it.fold(::handleFailure, ::handleSuccess) }

    private fun handleSuccess(firebaseResult: Any?) {
        when (firebaseResult) {
            is NewUserCreated -> Log.d(TAG, "handleSuccess:  saved new user to remote database")
            is ExistingUserLoaded -> Log.d(TAG, "handleSuccess: Successfully loaded existing user")
        }
        listenUserDetailsFirebaseChanges()
        listenUserPhotosFirebaseChanges()
    }

    fun createDialogs()
    {
        val dialogs = DialogsFixtures.dialogs

       // firebaseRepository.userFriendsCollection.document("ELO").set(dialogs[0])

//        for (dialog in dialogs)
//        {
//            firebaseRepository.userFriendsCollection.document(Random.nextInt(0,10000).toString()).set(dialog)
//        }

    }

    fun handleFailure(e: Failure) {
        Log.d(TAG, "Failed with Exception: ${e.exception.javaClass.simpleName}")
        when (e.exception) {
            is UserFirebaseException.UserNotExisting -> {
                createUser(); Log.d(TAG, "Starting attempt to create new User")
            }
            //todo catch and define new exception handlers
            is UserFirebaseException.UnknownException -> Log.d(TAG, " Unhandled exception within FirebaseRepository: check logs")
            //TODO implement timeout exception with retry loadinguser few times
        }
    }

    private fun listenUserDetailsFirebaseChanges() {
        firebaseRepository.userDetailsDocument.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                userDetailsLiveData.value = snapshot.toObject(UserEntity::class.java)!!

            } else Log.d(TAG, "Current data: null")
        }
    }
    private fun listenUserPhotosFirebaseChanges() {
        firebaseRepository.userPhotosDocument.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                userPhotosLiveData.value = snapshot.toObject(UserPhotos::class.java)!!

            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    fun getUserDetailsLiveData() = userDetailsLiveData
    fun getUserPhotosLiveData() = userPhotosLiveData
}