package com.example.data

import android.util.Log
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.domain.UserRepository
import com.example.domain.entities.UserEntity
import com.example.domain.exception.UserFirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(var firebaseFirestore: FirebaseFirestore, var fbAuth: FirebaseAuth) :
    UserRepository {

    var userDocument = firebaseFirestore.collection("Users").document(fbAuth.currentUser!!.uid)
    var usersCollection = firebaseFirestore.collection("Users")

    private val TAG: String? = this.javaClass.name

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

    override suspend fun loadUser(): Either<Failure, UserEntity?> {
        var userEntity: UserEntity? = null
        lateinit var userFirebaseException: UserFirebaseException

        userDocument.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                userEntity = documentSnapshot.toObject(UserEntity::class.java)
                Log.d(TAG, "Document not null, loading existing user")
            } else {
                userFirebaseException = UserFirebaseException.UserNotExisting
            }
        }.addOnFailureListener { Log.d(TAG, "onFailureListener with exception : $it") }.await()

        return when (userEntity != null) {
            true -> Right(userEntity)
            false -> Left(Failure(userFirebaseException))
        }
    }

    override suspend fun createUser(): Either<Failure, UserEntity> {
        var isSuccess = false
        val newUserEntity = UserEntity().apply { id = fbAuth.currentUser!!.uid }
        var throwable: Throwable? = null

        userDocument
            .set(newUserEntity)
            .addOnSuccessListener { Log.d(TAG, "User Created Successfully"); isSuccess = true }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e); throwable = e }
            .addOnSuccessListener { isSuccess = true }
            .await()

        return when (isSuccess) {
            true -> Right(newUserEntity)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }
}
