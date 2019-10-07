package com.example.data

import android.util.Log
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.example.domain.SearchRepository
import com.example.domain.entities.FoundNewUser
import com.example.domain.entities.GenderPreference.BOTH
import com.example.domain.entities.UserEntity
import com.example.domain.exception.UserFirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    val firebaseFirestore: FirebaseFirestore
) :
    SearchRepository {
    // todo export paths as external dagger dependency
    private val TAG: String? = this.javaClass.name
    var usersCollection =
        firebaseFirestore.collection("Users")

    var alreadyFoundUsers: MutableList<String> =
        mutableListOf()

    override suspend fun findNextUser(currentUser: UserEntity): Either<Failure, FoundNewUser?> {

        var isSuccess = false
        var nextUser: FoundNewUser? = null

        usersCollection
            //check Age compatibility
            .whereGreaterThanOrEqualTo("age", currentUser.preferences_age_range_min)
            .whereLessThanOrEqualTo("age", currentUser.preferences_age_range_max)
            //check gender compatibility
            .whereEqualTo("gender", currentUser.preferences_gender)
            .get()
            .addOnSuccessListener { documents ->
                isSuccess = true
                for (document in documents) {

                    if (!alreadyFoundUsers.contains(document.id)) {
                        // filter if preferences of other user are matching
                        val maxAge =
                            currentUser.age <= document.data["preferences_age_range_max"] as Long
                        val minAge =
                            currentUser.age >= document.data["preferences_age_range_min"] as Long
                        val prefGender =
                            document.data["preferences_gender"].toString() == BOTH.toString() || document.data["preferences_gender"] == currentUser.gender.toString()

                        if (maxAge && minAge && prefGender) {
                            nextUser = document.toObject(FoundNewUser::class.java)

                            //todo check if it's working
                            alreadyFoundUsers.add(document.id)

                            return@addOnSuccessListener

                            //todo if user not found, reset list and try again
                        }
                    }
                }
            }
            .addOnFailureListener {
                printUnknownException(it)
            }.await()

        return when (isSuccess) {
            true -> Right(nextUser)
            false -> Left(Failure(UserFirebaseException.UnknownException))
        }
    }

    private fun printUnknownException(e: Exception) {
        Log.e(TAG, "Unhandled firebase exception: ${Log.getStackTraceString(e)}")
    }
}