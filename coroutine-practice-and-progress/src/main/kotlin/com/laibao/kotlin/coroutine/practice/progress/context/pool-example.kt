package com.laibao.kotlin.coroutine.practice.progress.context

import com.laibao.kotlin.coroutine.practice.progress.future.future
import com.laibao.kotlin.coroutine.practice.progress.run.runBlocking
import com.laibao.kotlin.coroutine.practice.progress.util.log
import kotlinx.coroutines.future.await
import kotlinx.coroutines.newFixedThreadPoolContext

fun main() = runBlocking(CommonPool) {

    // multithreaded pool
    val n = 4
    val compute = newFixedThreadPoolContext(n, "Compute")

    // start 4 coroutines to do some heavy computation
    val subs = Array(n) { i ->
        future(compute) {
            log("Starting computation #$i")
            Thread.sleep(1000) // simulate long running operation
            log("Done computation #$i")
        }
    }

    // await all of them
    subs.forEach { it.await() }
    log("Done all")
}