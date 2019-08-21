package com.example.domain

import arrow.core.Either
import arrow.core.Failure
import com.example.domain.entities.UserEntity
import com.example.domain.exception.FirebaseResult

interface UserRepository {

    suspend fun saveAge()

    suspend fun saveNickname()

    suspend fun saveGender()

    suspend fun uploadPhoto()

    suspend fun loadUser(): Either<Failure, FirebaseResult>

    suspend fun createUser(): Either<Failure, FirebaseResult>

    suspend fun updateAgePreference(preferenceRange : Pair<Int,Int>): Either<Failure, FirebaseResult>

    suspend fun updateGenderPreference(): Either<Failure, UserEntity>

}