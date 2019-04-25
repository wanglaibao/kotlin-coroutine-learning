package com.laibao.kotlin.coroutine.practice.progress.context

import com.laibao.kotlin.coroutine.practice.progress.future.future
import com.laibao.kotlin.coroutine.practice.progress.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.coroutines.newSingleThreadContext

fun main() {
    log("Starting MyEventThread")
    val context = newSingleThreadContext("MyEventThread")
    val future = future(context) {
        log("Hello, world!")

        val future1 = future(context) {
            log("f1 is sleeping")
            delay(1000) // sleep 1s
            log("f1 returns 1")
            1
        }

        val future2 = future(context) {
            log("f2 is sleeping")
            delay(1000) // sleep 1s
            log("f2 returns 2")
            2
        }

        log("I'll wait for both f1 and f2. It should take just a second!")

        val sum = future1.await() + future2.await()

        log("And the sum is $sum")
    }

    future.get()

    log("Terminated")
}