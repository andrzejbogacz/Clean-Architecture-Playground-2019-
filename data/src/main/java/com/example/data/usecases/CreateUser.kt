package com.example.data.usecases

import arrow.core.Either
import arrow.core.None
import arrow.core.Try
import com.example.data.interactor.UseCase
import com.example.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class CreateUser @Inject constructor(var userRepository: UserRepository) : UseCase<String, None>() {

    private val TAG: String? = this.javaClass.name
    @Inject
    lateinit var fbAuth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var storageRef: StorageReference

    override suspend fun run(params: None): Either<Try.Failure, String> = userRepository.createUser()
}

