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
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.domain.entities.UserPhotos
import com.example.loquicleanarchitecture.R
import com.example.loquicleanarchitecture.helper.loadCircularImage
import com.example.loquicleanarchitecture.helper.serializeToMap

@BindingAdapter(value = ["app:setPhoto", "app:setProgressbar"], requireAll = false)
fun photoSetter(view: ImageView, userPhotos: UserPhotos?, progressBar: ProgressBar?) {

    userPhotos?.run {
        val photoLink = getPhotoLinkForContainer("photo1", userPhotos)

        if (progressBar != null && !photoLink.isNullOrEmpty()) {
            progressBar.visibility = View.VISIBLE
            progressBar.bringToFront()
        }

        photoLink.isNullOrEmpty().run { view.setImageResource(android.R.color.white) }

        photoLink?.run {
            Glide
                .with(view.context)
                .load(this)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("TAG", "on Resource Ready...")

                        if (progressBar != null) {
                            Log.d("TAG", "Loaded photo : hide progress bar")
                            progressBar.visibility = View.GONE
                            view.setBackgroundColor(Color.TRANSPARENT)
                        }
                        return false
                    }
                })
                .transform(CenterCrop(), RoundedCorners(20))
                .into(view)
        }
    }
}

@BindingAdapter(value = ["app:setProfilePhoto"], requireAll = false)
fun drawerPhotoSetter(view: ImageView, userPhotos: UserPhotos?) {
    val context = view.context

    val photoLink = userPhotos?.let { getFirstPhotoAvailable(it) }

    if (photoLink.isNullOrEmpty()) {
        view.loadCircularImage(
            context.resources.getDrawable(R.drawable.ic_profile_white),
            3f,
            Color.WHITE
        )
    }

    photoLink?.run {
        view.loadCircularImage(photoLink, 3f, Color.WHITE)
    }
}

@BindingAdapter("app:visibilityRemoveIcon")
fun visibilityRemoveIcon(view: ImageView, userPhotos: UserPhotos) {
    val photoLink = getPhotoLinkForContainer(view.contentDescription.toString(), userPhotos)

    when (photoLink.isNullOrEmpty()) {
        true -> view.visibility = View.GONE
        false -> view.visibility = View.VISIBLE
    }
}

@BindingAdapter("app:visibilityAddIcon")
fun visibilityAddIcon(view: ImageView, userPhotos: UserPhotos) {
    val photoLink = getPhotoLinkForContainer(view.contentDescription.toString(), userPhotos)

    when (photoLink.isNullOrEmpty()) {
        true -> {
            view.visibility = View.VISIBLE; view.invalidate(); view.bringToFront()
        }
        false -> view.visibility = View.GONE
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

fun getFirstPhotoAvailable(userPhotos: UserPhotos): String? {

    val usermap: Map<String, String?> = userPhotos.serializeToMap()

    var existingString: String? = null
    for (v in usermap) {
        v.value?.run {
            Log.d(
                "TAG2",
                "found non null value of ${v.key} and  value ${v.value} "
            ); existingString = v.value
        }
        break
    }
    return existingString
}