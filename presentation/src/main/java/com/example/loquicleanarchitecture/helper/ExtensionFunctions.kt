package com.example.loquicleanarchitecture.helper

import androidx.lifecycle.*
import arrow.core.Failure
import com.example.loquicleanarchitecture.view.main.MainActivity
import kotlin.reflect.KFunction1


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: KFunction1<T, Unit>) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure) -> Unit) =
    liveData.observe(this, Observer(body))

inline fun <reified T : ViewModel> MainActivity.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProvider(this, factory)[T::class.java]
    vm.body()
    return vm
}