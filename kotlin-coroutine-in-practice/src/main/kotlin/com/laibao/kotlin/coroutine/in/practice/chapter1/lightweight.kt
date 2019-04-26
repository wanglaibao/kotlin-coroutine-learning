package com.laibao.kotlin.coroutine.`in`.practice.chapter1

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Init, suspend, and resume three coroutines. They will
 * potentially be resumed in different threads than the ones
 * they were created on.
 */
fun main() {

    println("${Thread.activeCount()} threads active at the start")

    val time = measureTimeMillis {
        createCoroutines(300)
    }

    println("${Thread.activeCount()} threads active at end")

    println("Took $time ms")
}

fun createCoroutines(amount: Int) = runBlocking{
    val jobs = ArrayList<Job>()

    for (i in 1..amount) {
        jobs += launch {
            println("Started $i in ${Thread.currentThread().name}")
            delay(1000)

            println("Finished $i in ${Thread.currentThread().name}")
        }
    }

    jobs.forEach {
        it.join()
    }
}