package com.example.data.interactor

import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.Main as Main1

abstract class UseCaseDefault<out Type> where Type : Any {

    abstract suspend fun run()

    operator fun invoke(onResult: (Unit) -> Unit = {}) {
        val job = GlobalScope.async(Default) { run() }
        GlobalScope.launch(Main1) { onResult(job.await()) }
    }
}