package com.example.domain

import arrow.core.Either
import arrow.core.Failure
import com.example.domain.entities.UserEntity

interface UserRepository {

    suspend fun saveAge()

    suspend fun saveNickname()

    suspend fun saveGender()

    suspend fun uploadPhoto()

    suspend fun loadUser(): Either<Failure, UserEntity?>

    suspend fun createUser(): Either<Failure, UserEntity>

}