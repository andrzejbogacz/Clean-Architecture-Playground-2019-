package com.example.domain

import arrow.core.Either
import arrow.core.Failure
import com.example.domain.entities.FoundNewUser
import com.example.domain.entities.UserEntity

interface SearchRepository {


    suspend fun findNextUser(currentUser: UserEntity): Either<Failure, FoundNewUser?>

}