package com.example.data

import android.util.Log
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.domain.UserRepository
import com.example.domain.entities.GenderPreference
import com.example.domain.entities.UserEntity
import com.example.domain.exception.FirebaseResult
import com.example.domain.exception.FirebaseResult.UserAgePreferencesChanged
import com.example.domain.exception.FirebaseResult.UserGenderPreferencesChanged
import com.example.domain.exception.UserFirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(firebaseFirestore: FirebaseFirestore, var fbAuth: FirebaseAuth) :
    UserRepository {
    private val TAG: String? = this.javaClass.name

    var userDocument = firebaseFirestore.collection("Users").document(fbAuth.currentUser!!.uid)

    override suspend fun updateAgePreference(preferenceRange: Pair<Int, Int>): Either<Failure, FirebaseResult> {
        var isSuccess = false

        userDocument
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

        userDocument
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

        userDocument.get().addOnSuccessListener { documentSnapshot ->
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
            true -> Right(FirebaseResult.ExistingUserLoaded)
            false -> Left(Failure(userFirebaseException))
        }
    }

    override suspend fun createUser(): Either<Failure, FirebaseResult> {
        var isSuccess = false
        val newUserEntity = UserEntity().apply { id = fbAuth.currentUser!!.uid}

        userDocument
            .set(newUserEntity)
            .addOnFailureListener { printUnknownException(it) }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(FirebaseResult.NewUserCreated)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    override suspend fun saveAge() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveNickname() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveGender() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun uploadPhoto() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun printUnknownException(e: Exception) {
        Log.e(TAG, "Unhandled firebase exception: ${Log.getStackTraceString(e)}")
    }
}
