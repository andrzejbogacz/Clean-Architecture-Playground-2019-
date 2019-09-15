package com.example.loquicleanarchitecture.di.chatlist

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import com.example.loquicleanarchitecture.view.chatlist.ChatlistViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatlistViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChatlistViewModel::class)
    abstract fun bindChatlistViewModel(viewModel: ChatlistViewModel): ViewModel
}
