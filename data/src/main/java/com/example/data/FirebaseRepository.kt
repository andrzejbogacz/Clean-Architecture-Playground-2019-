package com.example.data

import android.net.Uri
import android.util.Log
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.domain.UserRepository
import com.example.domain.entities.Gender
import com.example.domain.entities.GenderPreference
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.example.domain.exception.FirebaseResult
import com.example.domain.exception.FirebaseResult.*
import com.example.domain.exception.UserFirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    val fbAuth: FirebaseAuth,
    val userStorageReference: StorageReference
) :
    UserRepository {
    // todo export paths as external dagger dependency
    private val TAG: String? = this.javaClass.name
    var userDetailsDocument =
        firebaseFirestore.collection("Users").document(fbAuth.currentUser!!.uid)
    var userPhotosDocument =
        firebaseFirestore.collection("Users").document(fbAuth.currentUser!!.uid)
            .collection("Storage").document("myPhotos")

    override suspend fun uploadProfileUserPhoto(uriAndTag: Pair<String, String>): Either<Failure, FirebaseResult> {

        val uri = uriAndTag.first
        val tag = uriAndTag.second

        var photoDownloadLink: String? = null
        val photoInStorageReference = userStorageReference.child("images/$tag")

        var isSuccess = false
        val photoUri = Uri.parse(uri)

        val photo = photoInStorageReference.putFile(photoUri)
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        photo.storage.downloadUrl.addOnSuccessListener { photoDownloadLink = it.toString() }.await()

        photoDownloadLink?.run {
            userDetailsDocument
                .collection("Storage")
                .document("myPhotos")
                .update(tag, photoDownloadLink)
                .addOnFailureListener { isSuccess = false }
                .await()
        }

        return when (isSuccess) {
            true -> Right(UserProfilePhotoUploaded)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }


    override suspend fun deleteProfileUserPhoto(viewTag: String): Either<Failure, FirebaseResult> {

        var isSuccess = false

        userDetailsDocument
            .collection("Storage")
            .document("myPhotos")
            .update(viewTag, null)
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(UserProfilePhotoDeleted)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    override suspend fun updateUserAge(age: Int): Either<Failure, FirebaseResult> {
        var isSuccess = false

        userDetailsDocument
            .update(
                "age", age
            )
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(UserProfileNicknameChanged)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }


    override suspend fun updateProfileUserNickname(nickname: String): Either<Failure, FirebaseResult> {
        var isSuccess = false

        userDetailsDocument
            .update(
                "nickname", nickname
            )
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(UserProfileNicknameChanged)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    override suspend fun updateUserGender(gender: Gender): Either<Failure, FirebaseResult> {
        var isSuccess = false

        userDetailsDocument
            .update(
                "gender", gender
            )
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(UserProfileGenderChanged)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }


    override suspend fun updateAgePreference(preferenceRange: Pair<Int, Int>): Either<Failure, FirebaseResult> {
        var isSuccess = false

        userDetailsDocument
            .update(
                "preferences_age_range_min", preferenceRange.first,
                "preferences_age_range_max", preferenceRange.second
            )
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(UserAgePreferencesChanged)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    override suspend fun updateGenderPreference(preferenceGender: GenderPreference): Either<Failure, FirebaseResult> {
        var isSuccess = false

        userDetailsDocument
            .update(
                "preferences_gender", preferenceGender.name
            )
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(UserGenderPreferencesChanged)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    override suspend fun loadUser(): Either<Failure, FirebaseResult> {
        var userEntity: UserEntity? = null
        lateinit var userFirebaseException: UserFirebaseException

        userDetailsDocument.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                userEntity = documentSnapshot.toObject(UserEntity::class.java)
                Log.d(TAG, "Document not null, loading existing user")
            } else {
                userFirebaseException = UserFirebaseException.UserNotExisting
            }
        }
            .addOnFailureListener { printUnknownException(it) }
            .await()

        return when (userEntity != null) {
            true -> Right(ExistingUserLoaded)
            false -> Left(Failure(userFirebaseException))
        }
    }

    override suspend fun createUser(): Either<Failure, FirebaseResult> {
        var isSuccess = false

        val newUserEntity = UserEntity().apply { id = fbAuth.currentUser!!.uid }
        val newUserPhotos = UserPhotos()
        userDetailsDocument
            .set(newUserEntity)
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        userDetailsDocument
            .collection("Storage")
            .document("myPhotos")
            .set(newUserPhotos)
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()



        return when (isSuccess) {
            true -> Right(NewUserCreated)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    override suspend fun uploadPhoto() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun printUnknownException(e: Exception) {
        Log.e(TAG, "Unhandled firebase exception: ${Log.getStackTraceString(e)}")
    }
}