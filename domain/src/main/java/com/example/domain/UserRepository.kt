package com.example.domain

import arrow.core.Either
import arrow.core.Failure

interface UserRepository {

    suspend fun saveAge()

    suspend fun saveNickname()

    suspend fun saveGender()

    suspend fun uploadPhoto()

    suspend fun loadUser()

    fun createUser() : Either<Failure,String>

}