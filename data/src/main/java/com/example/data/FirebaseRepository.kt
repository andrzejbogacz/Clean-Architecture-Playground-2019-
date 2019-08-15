package com.example.data

import android.util.Log
import arrow.core.*
import arrow.data.ListK
import arrow.data.extensions.list.semigroupK.combineK
import arrow.typeclasses.Functor
import arrow.typeclasses.Monoid
import com.example.domain.UserRepository
import com.example.domain.entities.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepository @Inject constructor(var firebaseFirestore: FirebaseFirestore, var fbAuth: FirebaseAuth) :
    UserRepository {

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

    override suspend fun loadUser() {

        val hello = listOf('h', 'e', 'l', 'l', 'o')
        val commaSpace = listOf(',', ' ')
        val world = listOf('w', 'o', 'r', 'l', 'd')

        hello.combineK(commaSpace).combineK(world)
    }

    override fun createUser(): Either<Failure, String> {

        val check = firebaseFirestore.collection("users")
            .add(UserEntity(id = fbAuth.currentUser!!.uid))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        return when (check.isSuccessful) {
            true -> Right("String")
            false -> Left(Try.Failure(Throwable("elo")))
        }
    }

}
