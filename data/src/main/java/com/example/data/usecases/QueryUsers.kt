package com.example.data.usecases

import arrow.core.Either
import arrow.core.Failure
import com.example.data.interactor.UseCase
import com.example.domain.SearchRepository
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class QueryUsers @Inject constructor(var searchRepository: SearchRepository) :
    UseCase<Pair<UserEntity, UserPhotos>, UserEntity>() {

    private val TAG: String? = this.javaClass.name
    @Inject
    lateinit var fbAuth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var storageRef: StorageReference

    override suspend fun run(params: UserEntity): Either<Failure, Pair<UserEntity, UserPhotos>> =
        searchRepository.findNextUser(params)
}

