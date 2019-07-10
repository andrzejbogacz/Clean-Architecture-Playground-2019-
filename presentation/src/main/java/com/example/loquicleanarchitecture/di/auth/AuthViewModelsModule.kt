package com.example.loquicleanarchitecture.di.auth

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import com.example.loquicleanarchitecture.login.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}
