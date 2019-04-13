package com.laibao.kotlin.coroutine.suspendcompose

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    //sampleStart
    val time = measureTimeMillis {
        val one =  doSomethingUsefulOne()
        val two =  doSomethingUsefulTwo()
        val three = doSomethingUsefulThree()
        println("The answer is ${one + two + three}")
    }
    println("Completed in $time ms")
    //sampleEnd Completed in 5008 ms

}

suspend fun doSomethingUsefulOne(): Int {
    delay(3000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun doSomethingUsefulThree(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 59
}