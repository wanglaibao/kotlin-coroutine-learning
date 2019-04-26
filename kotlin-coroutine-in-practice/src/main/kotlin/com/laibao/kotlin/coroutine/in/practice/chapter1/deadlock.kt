package com.laibao.kotlin.coroutine.`in`.practice.chapter1

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

lateinit var jobA : Job

lateinit var jobB : Job


// This will never complete execution.
fun main() = runBlocking<Unit> {
    jobA = launch {
        delay(1000)
        // wait for JobB to finish
        jobB.join()
    }

    jobB = launch {
        // wait for JobA to finish
        jobA.join()
    }

    // wait for JobA to finish
    jobA.join()
    println("Finished")
}