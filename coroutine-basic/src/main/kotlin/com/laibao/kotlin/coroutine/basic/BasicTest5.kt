package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking { // this: CoroutineScope

    launch { // launch new coroutine in the scope of runBlocking
        delay(5000L)
        println("World!")
    }

    println("Hello,")
}