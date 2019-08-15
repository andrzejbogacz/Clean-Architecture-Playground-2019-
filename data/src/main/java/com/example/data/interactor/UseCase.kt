package com.example.data.interactor

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Try
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.Main as Main1

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = GlobalScope.async(Default) { run(params) }
        GlobalScope.launch(Main1) { onResult(job.await()) }
    }
}