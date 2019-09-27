package com.example.loquicleanarchitecture.di.chatlist

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import com.example.loquicleanarchitecture.view.main.viewPager.RandomChatsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatlistViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(RandomChatsViewModel::class)
    abstract fun bindChatlistViewModel(viewModel: RandomChatsViewModel): ViewModel
}
