package com.example.domain

import arrow.core.Either
import arrow.core.Failure
import com.example.domain.entities.UserEntity
import com.example.domain.entities.UserPhotos

interface SearchRepository {

    suspend fun findNextUser(currentUser: UserEntity): Either<Failure, Pair<UserEntity, UserPhotos>>
}