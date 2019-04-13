package com.laibao.kotlin.coroutine.chapter2

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

// Use a ThreadPoolDispatcher to run the coroutine
fun main() = runBlocking {
    val netDispatcher = newSingleThreadContext(name = "ServiceCall")

    val task = launch(netDispatcher) {
        printCurrentThread()
    }

    task.join()
    println("Running in thread [${Thread.currentThread().name}]")
}

suspend fun printCurrentThread() {
    delay(3000)
    println("Running in thread [${Thread.currentThread().name}]")
}