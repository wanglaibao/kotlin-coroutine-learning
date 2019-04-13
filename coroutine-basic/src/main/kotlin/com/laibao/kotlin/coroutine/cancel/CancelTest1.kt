package com.laibao.kotlin.coroutine.cancel

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {


    val job:Job = launch {
        delay(2000)
        println("我在干活呢，你在干么啊")
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(1000L)
        }
    }


    delay(13000L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")

}