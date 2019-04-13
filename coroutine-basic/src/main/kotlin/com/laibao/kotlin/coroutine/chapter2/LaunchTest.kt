package com.laibao.kotlin.coroutine.chapter2

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Use launch to start a coroutine that crashes.
fun main() = runBlocking {

    val task = launch {
        doSomething()
    }
    //task.cancel()
    task.join()
    println("completed")
}

suspend fun doSomething() {
    delay(100)
    println("Half-way to crash")
    delay(100)
    throw UnsupportedOperationException("Can't do")
}