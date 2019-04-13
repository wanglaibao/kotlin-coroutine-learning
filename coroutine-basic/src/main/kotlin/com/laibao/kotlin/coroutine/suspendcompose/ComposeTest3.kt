package com.laibao.kotlin.coroutine.suspendcompose

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking{

    //sampleStart
    val time = measureTimeMillis {
        val one: Deferred<Int> =  async (start = CoroutineStart.LAZY){ doSomethingUseful01() }
        val two: Deferred<Int> =  async (start = CoroutineStart.LAZY){ doSomethingUseful02() }
        val three: Deferred<Int> = async (start = CoroutineStart.LAZY){ doSomethingUseful03() }
        one.start()
        two.start()
        three.start()
        println("The answer is ${one.await() + two.await() + three.await()}")
    }
    println("Completed in $time ms")
    //sampleEnd Completed in 3015 ms
}

suspend fun doSomethingUseful01(): Int {
    delay(3000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUseful02(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun doSomethingUseful03(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 59
}