package com.example.domain.entities

data class UserEntity(
    var id: Int,
    var nickname: String,
    var gender: Gender = Gender.MALE,
    var age: Int,
    var preferences_gender: Gender = Gender.FEMALE,
    var preferences_age_range: IntRange = 1..10,
    var photos: Any
)

