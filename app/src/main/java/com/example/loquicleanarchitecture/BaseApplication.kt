package com.example.loquicleanarchitecture

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<DaggerApplication> {
    }

}
