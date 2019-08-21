package com.example.data.usecases

import arrow.core.Either
import arrow.core.Failure
import arrow.core.None
import com.example.data.interactor.UseCase
import com.example.domain.UserRepository
import com.example.domain.entities.UserEntity
import com.example.domain.exception.FirebaseResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class LoadUser @Inject constructor(var userRepository: UserRepository) : UseCase<FirebaseResult, None>() {

    private val TAG: String? = this.javaClass.name
    @Inject
    lateinit var fbAuth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var storageRef: StorageReference

    override suspend fun run(params: None): Either<Failure, FirebaseResult> = userRepository.loadUser()
}

