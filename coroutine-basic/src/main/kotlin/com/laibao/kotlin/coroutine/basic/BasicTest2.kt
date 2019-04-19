package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*

fun main() {

    GlobalScope.launch { // launch new coroutine in background and continue
        delay(1000L)
        println("World!")
    }

    println("Hello,") // main thread continues here immediately

    // but this expression blocks the main thread
    runBlocking {
        // we delay for 5 seconds to keep JVM alive
        delay(5000L)
        println("Hello,金戈")
        println("Hello,金戈")
        println("Hello,金戈")


    }

}

