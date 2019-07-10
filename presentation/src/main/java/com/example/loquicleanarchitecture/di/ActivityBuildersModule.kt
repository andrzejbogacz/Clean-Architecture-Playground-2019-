package com.example.loquicleanarchitecture.di

import com.example.loquicleanarchitecture.chat.ChatActivity
import com.example.loquicleanarchitecture.di.auth.AuthModule
import com.example.loquicleanarchitecture.di.auth.AuthScope
import com.example.loquicleanarchitecture.di.auth.AuthViewModelsModule
import com.example.loquicleanarchitecture.di.chat.ChatModule
import com.example.loquicleanarchitecture.di.chat.ChatScope
import com.example.loquicleanarchitecture.di.chat.ChatViewModelsModule
import com.example.loquicleanarchitecture.di.main.MainModule
import com.example.loquicleanarchitecture.di.main.MainScope
import com.example.loquicleanarchitecture.di.main.MainViewModelsModule
import com.example.loquicleanarchitecture.di.profile.ProfileModule
import com.example.loquicleanarchitecture.di.profile.ProfileScope
import com.example.loquicleanarchitecture.di.profile.ProfileViewModelsModule
import com.example.loquicleanarchitecture.login.AuthActivity
import com.example.loquicleanarchitecture.main.MainActivity
import com.example.loquicleanarchitecture.profile.ProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthModule::class, AuthViewModelsModule::class])
    internal abstract fun contributeLoginActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class, MainViewModelsModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ChatScope
    @ContributesAndroidInjector(modules = [ChatModule::class, ChatViewModelsModule::class])
    internal abstract fun contributeChatActivity(): ChatActivity

    @ProfileScope
    @ContributesAndroidInjector(modules = [ProfileModule::class, ProfileViewModelsModule::class])
    internal abstract fun contributeProfileActivity(): ProfileActivity


}