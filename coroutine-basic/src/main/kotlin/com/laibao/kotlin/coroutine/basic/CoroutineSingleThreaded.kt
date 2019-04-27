package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*

/**
 * kotlin-samples : https://github.com/s1monw1/kotlin-samples
 *
 * Concurrent Programming Style
 *
 *      Callback-based (JavaScript)
 *      Future/Promise-based (Java, JavaScript)
 *      Async/Await-based (C#) and more
 *
 *      Continuations : which are an abstract representation of the control state of a computer program
 *
 */
fun main() {
    val context = newSingleThreadContext("launch-SingleThread")
    runBlocking {
        val time1 = System.currentTimeMillis()
        var i = 0

        List(1_000_000) {
            CoroutineScope(context).launch(CoroutineName("myroutine")) {
                i += 1
            }
        }.forEach { it.join() }

        println("Value: $i, Duration: ${System.currentTimeMillis() - time1}")
    }
}