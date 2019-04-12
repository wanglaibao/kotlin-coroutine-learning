package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> { // start main coroutine

    GlobalScope.launch { // launch new coroutine in background and continue
        delay(1000L)
        println("Welcome to Kotlin Coroutine World!")
    }

    println("Hello,金戈") // main coroutine continues here immediately

    delay(2000L)      // delaying for 2 seconds to keep JVM alive
}