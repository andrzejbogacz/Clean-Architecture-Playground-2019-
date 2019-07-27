package com.example.domain

interface EverythingRepository {

    fun saveAge()
    fun saveNickname()
    fun saveGender()
    fun uploadPhoto()
    fun loadUser()
    fun loadMessages()

}