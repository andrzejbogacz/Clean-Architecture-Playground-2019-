package com.example.loquicleanarchitecture.di.chat

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.chat.ChatViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindMainViewModel(viewModel: ChatViewModel): ViewModel
}
