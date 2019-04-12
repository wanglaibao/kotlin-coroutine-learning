package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    launch {
        doHello()
        doWorld()
    }

    println("Hello,")
}

suspend fun doHello() {
    delay(2000L)
    println("Hello,")
    doWorld()
}

suspend fun doWorld() {
    delay(3000L)
    println("World!")
}