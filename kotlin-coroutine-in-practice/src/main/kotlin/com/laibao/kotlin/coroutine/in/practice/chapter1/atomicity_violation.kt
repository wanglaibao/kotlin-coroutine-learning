package com.laibao.kotlin.coroutine.`in`.practice.chapter1

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

// This method will often print values lower than 2100
fun main() = runBlocking {

    val workerA = async {
        increment(1000000)
    }

    val workerB = async {
        increment(1000000)
    }

    workerA.await()
    workerB.await()

    print("counter [$counter]")
}

var counter = 0

fun increment(by: Int){
    for (i in 0 until by) {
        counter++
    }
}