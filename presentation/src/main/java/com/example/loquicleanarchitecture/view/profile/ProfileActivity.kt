package com.example.loquicleanarchitecture.view.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionManager
import com.example.loquicleanarchitecture.R
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.toast

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        swapView(image1)
    }


    fun swapView(v: View) {
        TransitionManager.beginDelayedTransition(layout)
        placeholder.setContentId(v.id)
        toast(v.id)
    }
}
