package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit>{

    val job = GlobalScope.launch { // launch new coroutine and keep a reference to its Job
        delay(5000L)
        println("World!")
    }

    println("Hello,金戈啊，你这个傻子")
    // wait until child coroutine completes
    job.join()
}