package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import java.time.Instant
import java.time.ZoneId


fun main() {

    fun getTime(): Instant {
        return Instant.now().atZone(ZoneId.systemDefault()).toInstant()
    }

    println("${getTime()}: Start")

    // Start a coroutine
    GlobalScope.launch {
        delay(1000)
        println("${getTime()}: Hello from coroutine")
    }

    runBlocking {
        delay(1500)
        println("${getTime()}: Hello from second coroutine ")
    }
    println("${getTime()}: Stop")

    val deferred = (1..1_000_000).map { n ->
        GlobalScope.async { n }
    }

    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println("Sum: $sum")
    }
}