package com.example.loquicleanarchitecture.di

import com.example.loquicleanarchitecture.view.chat.ChatActivity
import com.example.loquicleanarchitecture.di.auth.AuthModule
import com.example.loquicleanarchitecture.di.auth.AuthScope
import com.example.loquicleanarchitecture.di.auth.AuthViewModelsModule
import com.example.loquicleanarchitecture.di.chat.ChatModule
import com.example.loquicleanarchitecture.di.chat.ChatScope
import com.example.loquicleanarchitecture.di.chat.ChatViewModelsModule
import com.example.loquicleanarchitecture.di.chatlist.ChatlistModule
import com.example.loquicleanarchitecture.di.chatlist.ChatlistScope
import com.example.loquicleanarchitecture.di.chatlist.ChatlistViewModelsModule
import com.example.loquicleanarchitecture.di.main.MainModule
import com.example.loquicleanarchitecture.di.main.MainScope
import com.example.loquicleanarchitecture.di.main.MainViewModelsModule
import com.example.loquicleanarchitecture.di.profile.ProfileModule
import com.example.loquicleanarchitecture.di.profile.ProfileScope
import com.example.loquicleanarchitecture.di.profile.ProfileViewModelsModule
import com.example.loquicleanarchitecture.view.login.AuthActivity
import com.example.loquicleanarchitecture.view.main.MainActivity
import com.example.loquicleanarchitecture.view.main.chatlist.ChatlistActivity
import com.example.loquicleanarchitecture.view.profile.ProfileActivity
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

    @ChatlistScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, ChatlistViewModelsModule::class])
    internal abstract fun contributeChatlistActivity(): ChatlistActivity


}