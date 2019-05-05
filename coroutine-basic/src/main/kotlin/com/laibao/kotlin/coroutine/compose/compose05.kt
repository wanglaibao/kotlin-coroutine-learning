package com.laibao.kotlin.coroutine.compose

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
//sampleStart
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
//sampleEnd
}

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUseful5() }
    val two = async { doSomethingUseful6() }
    one.await() + two.await()
}

suspend fun doSomethingUseful5(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUseful6(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}