package com.example.data

import android.util.Log
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.domain.ChatRepository
import com.example.domain.SearchRepository
import com.example.domain.entities.FoundUserDetails
import com.example.domain.entities.GenderPreference.BOTH
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.example.domain.exception.FirebaseResult
import com.example.domain.exception.UserFirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    firebaseFirestore: FirebaseFirestore
) :
    ChatRepository {

    // todo export paths as external dagger dependency
    private val TAG: String? = this.javaClass.name
    var usersCollection =
        firebaseFirestore.collection("Users")


    private suspend fun combineUserWithPhotos(userEntity: UserEntity): Either<Failure, Pair<UserEntity, UserPhotos>> {
        var isSuccess = false
        var userPhotos: UserPhotos? = null
        val user = userEntity
        lateinit var pairUserAndPhotos: Pair<UserEntity, UserPhotos>

        usersCollection.document(user.id!!)
            .collection("Storage")
            .document("myPhotos")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                userPhotos = documentSnapshot.toObject(UserPhotos::class.java)
                pairUserAndPhotos = Pair(userEntity, userPhotos!!)
                isSuccess = true

                Log.e(TAG, pairUserAndPhotos.toString())

            }.await()

        return when (isSuccess) {
            true -> Right(pairUserAndPhotos)
            //todo false
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    private fun printUnknownException(e: Exception) {
        Log.e(TAG, "Unhandled firebase exception: ${Log.getStackTraceString(e)}")
    }
}