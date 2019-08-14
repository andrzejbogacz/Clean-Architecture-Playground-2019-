package com.example.loquicleanarchitecture.view.main

import arrow.core.None
import com.example.data.usecases.CreateUser
import com.example.loquicleanarchitecture.view.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(val createUser: CreateUser) : BaseViewModel() {

    fun createUser() = createUser(params = None)
}