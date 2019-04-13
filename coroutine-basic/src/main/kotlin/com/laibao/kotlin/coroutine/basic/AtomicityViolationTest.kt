package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

// This method will often print values lower than 2100
fun main(args: Array<String>) = runBlocking {

    val workerA = async {  asyncIncrement(2000) }

    val workerB = async {  asyncIncrement(100) }

    workerA.await()

    workerB.await()

    print("counter [$counter]")
}

var counter = 0

suspend fun asyncIncrement(by: Int) {
    for (i in 0 until by) {
        counter++
    }
}