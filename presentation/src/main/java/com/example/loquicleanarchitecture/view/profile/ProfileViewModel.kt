package com.example.loquicleanarchitecture.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(): ViewModel() {

    private val ageNumber = MutableLiveData<Int>().apply { }
    private val nickname = MutableLiveData<String>().apply { }
    private val gender = MutableLiveData<Int>().apply { }


    fun setAgeNumber(num: Int) {
        ageNumber.value = num
    }

    fun setNicknameValue(num: String) {
        nickname.value = num
    }

    fun setGender(num: Int) {
        gender.value = num
    }



}
