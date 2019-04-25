package com.laibao.kotlin.coroutine.practice.progress.context

import com.laibao.kotlin.coroutine.practice.progress.run.launch
import com.laibao.kotlin.coroutine.practice.progress.util.log
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() {
    launch(Swing) {
        try {
            // suspend while asynchronously making request
            val result = makeRequest()
            // display result in UI, here Swing context ensures that we always stay in event dispatch thread
            display(result)
        } catch (exception: Throwable) {
            // process exception
        }
    }
}

suspend fun makeRequest(): String {
    log("Making request...")
    return suspendCoroutine { c ->
        ForkJoinPool.commonPool().execute {
            c.resume("Result of the request")
        }
    }
}

fun display(result: String) {
    log("Displaying result '$result'")
}