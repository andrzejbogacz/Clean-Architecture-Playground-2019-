package com.example.domain

import arrow.core.Either
import arrow.core.Failure
import com.example.domain.entities.Gender
import com.example.domain.entities.GenderPreference
import com.example.domain.exception.FirebaseResult

interface UserRepository {

    suspend fun loadUser(): Either<Failure, FirebaseResult>

    suspend fun createUser(): Either<Failure, FirebaseResult>

    suspend fun updateAgePreference(preferenceRange: Pair<Int, Int>): Either<Failure, FirebaseResult>

    suspend fun updateGenderPreference(preferenceGender: GenderPreference): Either<Failure, FirebaseResult>

    suspend fun updateUserGender(gender: Gender): Either<Failure, FirebaseResult>

    suspend fun updateUserAge(age: Int): Either<Failure, FirebaseResult>

    suspend fun updateProfileUserNickname(nickname: String): Either<Failure, FirebaseResult>

    suspend fun uploadProfileUserPhoto(uriAndTag: Pair<String, String>): Either<Failure, FirebaseResult>

    suspend fun deleteProfileUserPhoto(viewTag: String): Either<Failure, FirebaseResult>
}