package com.example.loquicleanarchitecture.di.chat

import androidx.lifecycle.ViewModel
import com.example.loquicleanarchitecture.di.viewmodels.ViewModelKey
import com.example.loquicleanarchitecture.view.chat.ChatRoomViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChatRoomViewModel::class)
    abstract fun bindMainViewModel(viewModel: ChatRoomViewModel): ViewModel
}
