package com.example.loquicleanarchitecture.di

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
import com.example.loquicleanarchitecture.view.chat.ChatRoomFragment
import com.example.loquicleanarchitecture.view.chatlist.ChatlistFragment
import com.example.loquicleanarchitecture.view.dialogs.*
import com.example.loquicleanarchitecture.view.login.AuthActivity
import com.example.loquicleanarchitecture.view.main.MainActivity
import com.example.loquicleanarchitecture.view.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthModule::class, AuthViewModelsModule::class])
    internal abstract fun contributeLoginActivity(): AuthActivity

    /** MAIN SCOPE */
    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class, MainViewModelsModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @MainScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, MainViewModelsModule::class])
    internal abstract fun contributeSearchAgeDialog(): DialogDrawerSearchAge

    @MainScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, MainViewModelsModule::class])
    internal abstract fun contributeSearchGenderDialog(): DialogDrawerSearchGender

    @MainScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, MainViewModelsModule::class])
    internal abstract fun contributeChangeNicknameDialog(): DialogProfileNickname

    @MainScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, MainViewModelsModule::class])
    internal abstract fun contributeChangeGenderDialog(): DialogProfileGenderChoice

    @MainScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, MainViewModelsModule::class])
    internal abstract fun contributeChangeAgeDialog(): DialogProfileAge

    /** END OF MAIN SCOPE */

    @ChatScope
    @ContributesAndroidInjector(modules = [ChatModule::class, ChatViewModelsModule::class])
    internal abstract fun contributeChatRoomFragment(): ChatRoomFragment

    @ProfileScope
    @ContributesAndroidInjector(modules = [ProfileModule::class, ProfileViewModelsModule::class])
    internal abstract fun contributeProfileActivity(): ProfileFragment

    @ChatlistScope
    @ContributesAndroidInjector(modules = [ChatlistModule::class, ChatlistViewModelsModule::class])
    internal abstract fun contributeChatlistActivity(): ChatlistFragment
}