package com.example.domain.exception


sealed class Result

sealed class FirebaseResult : Result() {
    object UserAgePreferencesChanged : FirebaseResult()
    object ExistingUserLoaded : FirebaseResult()
    object NewUserCreated : FirebaseResult()
    object UserDataHasChanged : FirebaseResult()
    object UserGenderPreferencesChanged : FirebaseResult()
    object UserProfileNicknameChanged : FirebaseResult()
    object UserProfileGenderChanged : FirebaseResult()
    object UserProfileAgeChanged : FirebaseResult()
    object UserProfilePhotoUploaded : FirebaseResult()
    object UserProfilePhotoDeleted : FirebaseResult()
}