package com.example.loquicleanarchitecture.main

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.constraintLayout

class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        constraintLayout {
        }
    }
}