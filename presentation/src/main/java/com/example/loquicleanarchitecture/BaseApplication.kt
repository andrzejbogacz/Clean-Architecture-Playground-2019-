package com.example.loquicleanarchitecture

import com.example.data_room.db.AppExecutors
import com.example.data_room.db.DataRepository
import com.example.data_room.db.db.AppDatabase
import com.example.loquicleanarchitecture.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    private var mAppExecutors: AppExecutors? = null

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        mAppExecutors = AppExecutors()
    }

    fun getDatabase(): AppDatabase {
        return AppDatabase.getInstance(this, mAppExecutors!!)
    }

    fun getRepository(): DataRepository {
        return DataRepository.getInstance(getDatabase())
    }
}