package com.laibao.kotlin.coroutine.chapter2

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Async examples
 */
@InternalCoroutinesApi
fun main() = runBlocking {

    val task = async {
        doSomethingOther()
    }

    // This code will have the exception be propagated
    task.await()
    println("Completed")


    // This code will wait for the async to end and validate its output.
    // Comment line `15` for this to work
    task.join()

    // If the code below executes the `else` branch, please replace
    // `isCancelled` with `isCompletedExceptionally`. For more info see
    // https://github.com/Kotlin/kotlinx.coroutines/issues/220
    if (task.isCancelled) {
        val exception = task.getCancellationException()
        println("Error with message: ${exception.message}")
    } else {
        println("Success")
    }
}

suspend fun doSomethingOther() {
    delay(2000)
    throw UnsupportedOperationException("Can't do")
}