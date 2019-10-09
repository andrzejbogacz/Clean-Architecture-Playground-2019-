package com.example.domain.entities

open class UserEntity(
    var id: String? = null,
    var nickname: String? = "anonymous",
    var gender: Gender = Gender.MALE,
    var age: Int = 18,
    var preferences_gender: GenderPreference = GenderPreference.FEMALE,
    var preferences_age_range_min: Int = 20,
    var preferences_age_range_max: Int = 40
)

class FoundUserDetails : UserEntity()
