/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.loquicleanarchitecture.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.domain.entities.Gender
import com.example.domain.entities.GenderPreference
import com.example.loquicleanarchitecture.R

@BindingAdapter(value = ["app:setNickname"], requireAll = false)
fun nicknameSetter(view: TextView, nickname: String?) {
    nickname?.run { view.text = nickname }
}

@BindingAdapter(value = ["app:setGender"], requireAll = false)
fun genderSetter(view: TextView, gender: Gender?) {
    val context = view.context

    when (gender) {
        Gender.MALE -> view.text = context.resources.getString(R.string.profile_gender_male)
        Gender.FEMALE -> view.text = context.resources.getString(R.string.profile_gender_female)
    }
}

@BindingAdapter(value = ["app:setAge"], requireAll = false)
fun ageSetter(view: TextView, age: Int?) {

    age?.run { view.text = age.toString() }
}

@BindingAdapter(value = ["app:setGenderPreferences"], requireAll = false)
fun genderPreferenceSetter(view: TextView, genderPreference: GenderPreference?) {
    val context = view.context

    when (genderPreference) {
        GenderPreference.FEMALE -> context.resources.getString(R.string.drawer_dialog_genderFemale)
        GenderPreference.MALE -> context.resources.getString(R.string.drawer_dialog_genderMale)
        GenderPreference.BOTH -> context.resources.getString(R.string.drawer_dialog_genderBoth)
    }
}