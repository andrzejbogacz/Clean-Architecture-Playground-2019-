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

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.domain.entities.Gender
import com.example.domain.entities.UserPhotos
import com.example.loquicleanarchitecture.R

object BindingAdapters {
    /**
     * A Binding Adapter that is called whenever the value of the attribute `app:popularityIcon`
     * changes. Receives a popularity level that determines the icon and tint color to use.
     */
    val TAG = this.javaClass.name

    @BindingAdapter("app:setPhoto")
    @JvmStatic
    fun setPhoto(view: ImageView, userPhotos: UserPhotos) {
        val photoLink = getPhotoLinkForContainer(view.contentDescription.toString(), userPhotos)

        photoLink.isNullOrEmpty().run { view.setImageResource(android.R.color.white) }

        photoLink?.run {
            Glide.with(view.context)
                .load(this)
                .transform(CenterCrop(), RoundedCorners(20))
                // todo gif loading
                .placeholder(R.drawable.ic_action_copy)
                .into(view.also { it.setBackgroundColor(Color.TRANSPARENT) })
        }
    }

    private fun getPhotoLinkForContainer(
        photoTag: String, userPhotos: UserPhotos
    ): String? {
        return when (photoTag) {
            "photo1" -> return userPhotos.photo1
            "photo2" -> return userPhotos.photo2
            "photo3" -> return userPhotos.photo3
            "photo4" -> return userPhotos.photo4
            "photo5" -> return userPhotos.photo5
            "photo6" -> return userPhotos.photo6
            else -> null
        }
    }

    @BindingAdapter("app:visibilityRemoveIcon")
    @JvmStatic
    fun visiblityRemoveIcon(view: ImageView, userPhotos: UserPhotos) {
        val photoLink = getPhotoLinkForContainer(view.contentDescription.toString(), userPhotos)

        when (photoLink.isNullOrEmpty()) {
            true -> view.visibility = View.GONE
            false -> view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("app:visibilityAddIcon")
    @JvmStatic
    fun visiblityAddIcon(view: ImageView, userPhotos: UserPhotos) {
        val photoLink = getPhotoLinkForContainer(view.contentDescription.toString(), userPhotos)

        when (photoLink.isNullOrEmpty()) {
            true -> {
                view.visibility = View.VISIBLE; view.invalidate(); view.bringToFront()
            }
            false -> view.visibility = View.GONE
        }
    }

    @BindingAdapter("app:nickname")
    @JvmStatic
    fun setNicknameValue(view: TextView, nickname: String) {
        val context = view.context
        when (nickname.isNullOrEmpty()) {
            true -> view.text = context.resources.getString(R.string.anonymous)
            false -> view.text = nickname
            // }
        }
    }

    @BindingAdapter("app:age")
    @JvmStatic
    fun setAgeValue(view: TextView, age: Int) {
        view.text = age.toString()
    }

    @BindingAdapter("app:gender")
    @JvmStatic
    fun setGenderValue(view: TextView, gender: Gender) {
        val context = view.context
        when (gender) {
            Gender.FEMALE -> view.text = context.resources.getString(R.string.profile_gender_female)
            Gender.MALE -> view.text = context.resources.getString(R.string.profile_gender_male)
        }
    }
}
