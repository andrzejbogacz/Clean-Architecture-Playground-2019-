package com.example.domain

interface UserRepository {

    fun saveAge()

    fun saveNickname()

    fun saveGender()

    fun uploadPhoto()

    fun loadUser()

}