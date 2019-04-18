package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        doWorksInSeries()
        doWorksInParallel()
    }
}

suspend fun testContinuation() {
    println("-----------------")
}

// Parallel execution
suspend private fun doWorksInParallel() = runBlocking{
    val one = async {
        doWorkFor1Seconds()
    }
    val two = async {
        doWorkFor2Seconds()
    }

    val combined = one.await() + "_" + two.await()

    println("Kotlin Combined : " + combined)

}


// Serial execution
suspend private fun doWorksInSeries() {
   val one = doWorkFor1Seconds()
   val two = doWorkFor2Seconds()
   println("Kotlin One : " + one)
   println("Kotlin Two : " + two)
}


suspend fun doWorkFor1Seconds(): String {
    delay(1000)
    return "doWorkFor1Seconds"
}
suspend fun doWorkFor2Seconds(): String {
    delay(2000)
    return "doWorkFor2Seconds"
}