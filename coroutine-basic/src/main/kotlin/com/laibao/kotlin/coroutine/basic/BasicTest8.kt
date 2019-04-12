package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launchDoWorld()
    println("Hello,")
}

// this is your first suspending function
suspend fun launchDoWorld() = coroutineScope {
    launch {
        println("World!")
    }
}