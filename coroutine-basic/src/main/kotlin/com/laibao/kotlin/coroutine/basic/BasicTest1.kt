package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


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

    runBlocking {

        (1 .. 5000000).forEach{
            launch {
                delay(5000)
                println("协程的能力测试")
            }
        }

        async {  }
    }


    thread {
        /**
         * Error: Kotlin: Suspend functions are only allowed to
         *
         * be called from a coroutine or another suspend function
         */

        //delay(1000L)

        TimeUnit.SECONDS.sleep(3)

        println("World! World! World! World! World!")      // main thread continues while coroutine is delayed
    }
    
    println("Hello,")
    TimeUnit.SECONDS.sleep(5)


}