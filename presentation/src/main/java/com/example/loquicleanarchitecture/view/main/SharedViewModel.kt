package com.example.loquicleanarchitecture.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import arrow.core.Failure
import arrow.core.None
import com.example.data.FirebaseRepository
import com.example.data.usecases.*
import com.example.domain.entities.Gender
import com.example.domain.entities.GenderPreference
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.example.domain.exception.FirebaseResult.*
import com.example.domain.exception.UserFirebaseException
import com.example.loquicleanarchitecture.view.BaseViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    val createUser: CreateUser,
    val loadUser: LoadUser,
    val changeUserAgePreference: ChangeUserAgePreference,
    val changeUserGenderPreference: ChangeUserGenderPreference,
    val changeProfileUserNickname: ChangeProfileUserNickname,
    val changeProfileUserGender: ChangeProfileUserGender,
    val changeProfileUserAge: ChangeProfileUserAge,
    val uploadProfileUserPhoto: UploadProfileUserPhoto,
    val deleteProfileUserPhoto: DeleteProfileUserPhoto,
    private var firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    private val TAG: String? = this.javaClass.name

    private val userDetailsLiveData: MutableLiveData<UserEntity> = MutableLiveData()
    private val userPhotosLiveData: MutableLiveData<UserPhotos> = MutableLiveData()

    fun uploadProfileUserPhoto(imageUri: Pair<String, String>) = uploadProfileUserPhoto(imageUri) { it.fold(::handleFailure, ::handleSuccess) }

    fun changeUserGenderPreference(gender: GenderPreference) = changeUserGenderPreference(gender) { it.fold(::handleFailure, ::handleSuccess) }

    fun changeProfileUserNickname(nickname: String) = changeProfileUserNickname(nickname) { it.fold(::handleFailure, ::handleSuccess) }

    fun changeUserAgePreference(pair: Pair<Int, Int>) = changeUserAgePreference(pair) { it.fold(::handleFailure, ::handleSuccess) }

    fun changeProfileUserGender(gender: Gender) = changeProfileUserGender(gender) { it.fold(::handleFailure, ::handleSuccess) }

    fun changeProfileUserAge(age: Int) = changeProfileUserAge(age) { it.fold(::handleFailure, ::handleSuccess) }

    private fun createUser() = createUser(None) { it.fold(::handleFailure, ::handleSuccess) }

    fun loadUser() = loadUser(None) { it.fold(::handleFailure, ::handleSuccess) }

    private fun handleSuccess(firebaseResult: Any) {
        when (firebaseResult)
        {
            is NewUserCreated ->
            {
                listenUserDetailsFirebaseChanges() ; listenUserPhotosFirebaseChanges()
                Log.d(TAG, "handleSuccess: Successfully saved new user to remote database")
            }

            is ExistingUserLoaded ->
            {
                listenUserDetailsFirebaseChanges(); listenUserPhotosFirebaseChanges()
                Log.d(TAG, "handleSuccess: Successfully loaded existing user")
            }

            is UserAgePreferencesChanged -> Log.d(TAG, "handleSuccess: Successfully changed user age preference")
            is UserProfileNicknameChanged -> Log.d(TAG, "handleSuccess: Successfully changed user nickname")
            is UserProfileGenderChanged -> Log.d(TAG, "handleSuccess: Successfully changed user gender")
            is UserProfileAgeChanged -> Log.d(TAG, "handleSuccess: Successfully changed user age")
            is UserProfilePhotoUploaded -> Log.d(TAG, "handleSuccess: Successfully uploaded photo")
        }
    }

    fun handleFailure(e: Failure) {
        Log.d(TAG, "Failed Loading user with Exception: ${e.exception.javaClass.simpleName}")
        when (e.exception) {
            is UserFirebaseException.UserNotExisting -> { createUser() ; Log.d(TAG, "Starting attempt to create new User") }

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