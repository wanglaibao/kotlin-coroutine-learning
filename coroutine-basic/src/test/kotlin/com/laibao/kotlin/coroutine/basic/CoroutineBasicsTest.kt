package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.TimeUnit

class CoroutineBasicsTest {

    @Test
    fun testGlobalScope1() {

        /**
         * Here we are launching a new coroutine in the GlobalScope,
         * meaning that the lifetime of the new coroutine is limited
         * only by the lifetime of the whole application
         */
        val job = GlobalScope.launch {
            delay(1000L)
            println("World!")
        }

        println("Hello,")
        TimeUnit.SECONDS.sleep(2)
    }

    /**
     * Global coroutines are like daemon threads
     */
    @Test
    fun testGlobalScope2() = runBlocking {
        //sampleStart

        GlobalScope.launch {
            repeat(1000000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(3000L) // just quit after delay

        //sampleEnd
    }
}