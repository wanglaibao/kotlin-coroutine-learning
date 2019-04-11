package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


fun main() {

    /**
     * launch new coroutine in background and continue
     */
    GlobalScope.launch {
        // non-blocking delay for 1 second (default time unit is ms)
        delay(1000L)
        // println after delay
        println("World!")
    }

    // main thread continues while coroutine is delayed
    println("Hello,")
    TimeUnit.SECONDS.sleep(2)
}