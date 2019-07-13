package com.example.loquicleanarchitecture.di.profile

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import com.example.loquicleanarchitecture.view.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindMainViewModel(viewModel: ProfileViewModel): ViewModel
}
