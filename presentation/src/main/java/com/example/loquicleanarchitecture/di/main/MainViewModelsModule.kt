package com.example.loquicleanarchitecture.di.main

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import com.example.loquicleanarchitecture.view.main.MainActivityViewModel
import com.example.loquicleanarchitecture.view.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}
