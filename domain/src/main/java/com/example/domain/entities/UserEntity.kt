package com.example.domain.entities

data class UserEntity(
    var id: String? = null,
    var nickname: String? = null,
    var gender: Gender = Gender.MALE,
    var age: Int = 18,
    var preferences_gender: Gender = Gender.FEMALE,
    var preferences_age_range: Int = 20,
    var photos: Any? = null
)

