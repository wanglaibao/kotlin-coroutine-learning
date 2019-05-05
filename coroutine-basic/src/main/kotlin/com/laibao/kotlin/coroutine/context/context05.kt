package com.laibao.kotlin.coroutine.context

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
//sampleStart
    val job = coroutineContext[Job]
    println("My job is $job")
//sampleEnd
}