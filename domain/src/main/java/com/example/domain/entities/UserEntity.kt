package com.example.domain.entities

data class UserEntity(
    var id: String? = null,
    var nickname: String? = "Anonymous",
    var gender: Gender = Gender.MALE,
    var age: Int = 18,
    var preferences_gender: GenderPreference = GenderPreference.FEMALE,
    var preferences_age_range_min: Int = 20,
    var preferences_age_range_max: Int = 40,
    var photos: Any? = null
)