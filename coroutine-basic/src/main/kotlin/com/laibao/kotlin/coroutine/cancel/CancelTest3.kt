package com.laibao.kotlin.coroutine.cancel

import kotlinx.coroutines.*

fun main() = runBlocking {

    //sampleStart
    val startTime = System.currentTimeMillis()

    val job: Job = launch (Dispatchers.Default){
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }

    delay(30000L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    //job.join()
    println("main: Now I can quit.")
    //sampleEnd
}