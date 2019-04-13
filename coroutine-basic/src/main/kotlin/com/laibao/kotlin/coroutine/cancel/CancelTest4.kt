package com.laibao.kotlin.coroutine.cancel

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    val job = launch {
        try {
            repeat(100000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("I'm running finally")
            delay(1000)
        }
    }

    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")

    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}