package com.laibao.kotlin.coroutine.cancel

import kotlinx.coroutines.*

fun main() = runBlocking {
    //sampleStart
    val startTime = System.currentTimeMillis()

    val job:Job = launch (Dispatchers.Default){
        delay(2000L)
        var nextPrintTime = startTime
        var i = 0
        while (i <= 50) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }

    delay(3000L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    //job.join()
    println("main: Now I can quit.")
    //sampleEnd
}