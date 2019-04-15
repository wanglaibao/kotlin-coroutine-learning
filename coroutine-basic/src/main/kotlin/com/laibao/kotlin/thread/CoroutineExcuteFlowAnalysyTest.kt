package com.laibao.kotlin.thread

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main() = runBlocking{

    val job : Job = launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        delay(20000)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        //delay(20000)
        TimeUnit.SECONDS.sleep(20)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        TimeUnit.SECONDS.sleep(20)
        println("执行之后${Thread.currentThread().name}")
    }

    launch {
        println("执行之前${Thread.currentThread().name}")
        TimeUnit.SECONDS.sleep(20)
        println("执行之后${Thread.currentThread().name}")
    }
    job.join()

}