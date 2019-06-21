package com.example.loquicleanarchitecture.di

import com.example.loquicleanarchitecture.di.auth.AuthModule
import com.example.loquicleanarchitecture.di.auth.AuthScope
import com.example.loquicleanarchitecture.di.auth.AuthViewModelsModule
import com.example.loquicleanarchitecture.login.AuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthModule::class, AuthViewModelsModule::class])
    internal abstract fun contributeLoginActivity(): AuthActivity
}