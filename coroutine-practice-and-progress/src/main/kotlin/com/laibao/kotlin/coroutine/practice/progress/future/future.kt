package com.laibao.kotlin.coroutine.practice.progress.future

import com.laibao.kotlin.coroutine.practice.progress.context.CommonPool
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.*

fun <T> future(context: CoroutineContext = CommonPool, block: suspend () -> T): CompletableFuture<T> =
        CompletableFutureCoroutine<T>(context).also { block.startCoroutine(completion = it) }



class CompletableFutureCoroutine<T>(override val context: CoroutineContext) : CompletableFuture<T>(), Continuation<T> {
    override fun resumeWith(result: Result<T>) {
        result
                .onSuccess { complete(it) }
                .onFailure { completeExceptionally(it) }
    }
}