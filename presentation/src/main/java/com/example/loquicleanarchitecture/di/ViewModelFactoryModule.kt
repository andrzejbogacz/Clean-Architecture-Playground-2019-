package com.example.loquicleanarchitecture.di

import androidx.lifecycle.ViewModelProvider
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}
