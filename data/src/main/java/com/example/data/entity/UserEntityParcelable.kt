package com.example.data.entity

import android.os.Parcelable
import com.example.domain.entities.Gender
import com.example.domain.entities.GenderPreference
import com.example.domain.entities.UserEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntityParcelable(
    var id: String? = null,
    var nickname: String? = "anonymous",
    var gender: Gender = Gender.MALE,
    var age: Int = 18,
    var preferences_gender: GenderPreference = GenderPreference.FEMALE,
    var preferences_age_range_min: Int = 20,
    var preferences_age_range_max: Int = 40
) : Parcelable

fun UserEntity.mapToDataLayer(): UserEntityParcelable = UserEntityParcelable(
    id,
    nickname,
    gender,
    age,
    preferences_gender,
    preferences_age_range_min,
    preferences_age_range_max
)