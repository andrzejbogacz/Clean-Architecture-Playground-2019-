package com.example.loquicleanarchitecture.view.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Failure
import com.example.data.usecases.ChangeProfileUserAge
import com.example.data.usecases.ChangeProfileUserGender
import com.example.data.usecases.ChangeProfileUserNickname
import com.example.data.usecases.UploadProfileUserPhoto
import com.example.domain.entities.UserEntity
import com.example.domain.exception.FirebaseResult
import com.example.domain.exception.UserFirebaseException
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : ViewModel() {

    private val TAG: String? = this.javaClass.name

    private val userData: MutableLiveData<UserEntity> = MutableLiveData()

    fun handleFailure(e: Failure) {
        Log.d(TAG, "Failed Loading user with Exception: ${e.exception.javaClass.simpleName}")

        when (e.exception) {
            //todo catch and define new exception handlers
            is UserFirebaseException.UnknownException -> Log.d(TAG, " Unhandled exception within FirebaseRepository: check logs")
        }
    }

    private fun handleSuccess(firebaseResult: Any) {
        when (firebaseResult) {
            is FirebaseResult.UserAgePreferencesChanged -> Log.d(TAG, "handleSuccess: Successfully changed user age preference")
            is FirebaseResult.UserProfileNicknameChanged -> Log.d(TAG, "handleSuccess: Successfully changed user nickname")
            is FirebaseResult.UserProfileGenderChanged -> Log.d(TAG, "handleSuccess: Successfully changed user gender")
            is FirebaseResult.UserProfileAgeChanged -> Log.d(TAG, "handleSuccess: Successfully changed user age")
            is FirebaseResult.UserProfilePhotoUploaded -> Log.d(TAG, "handleSuccess: Successfully uploaded photo")
        }
    }

    fun getUserDataLiveData(): MutableLiveData<UserEntity> {
        return userData
    }
}