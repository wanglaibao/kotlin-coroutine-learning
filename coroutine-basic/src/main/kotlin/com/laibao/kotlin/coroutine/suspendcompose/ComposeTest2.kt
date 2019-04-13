package com.laibao.kotlin.coroutine.suspendcompose

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    //sampleStart
    val time = measureTimeMillis {
        val one:Deferred<Int> =  async { doSomethingUseful1() }
        val two:Deferred<Int> =  async { doSomethingUseful2() }
        val three:Deferred<Int> = async { doSomethingUseful3() }
        println("The answer is ${one.await() + two.await() + three.await()}")
    }
    println("Completed in $time ms")
    //sampleEnd Completed in 3024 ms

}


suspend fun doSomethingUseful1(): Int {
    delay(3000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUseful2(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun doSomethingUseful3(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 59
}