package com.example.loquicleanarchitecture.helper

import android.graphics.Color
import com.makeramen.roundedimageview.RoundedTransformationBuilder

object ImageTransformation {
    var transformation = RoundedTransformationBuilder()
        .borderColor(Color.GRAY)
        .borderWidthDp(3f)
        .cornerRadiusDp(7f)
        .oval(false)
        .build()

}