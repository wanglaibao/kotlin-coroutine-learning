package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BasicTest1 {

    @Test
    fun testMySuspendingFunction() = runBlocking<Unit> {

        // here we can use suspending functions using any assertion style that we like
        GlobalScope.launch { // launch new coroutine in background and continue
            delay(1000L)
            println("Welcome to Kotlin Coroutine World!")
        }

        println("Hello,金戈") // main coroutine continues here immediately

        delay(2000L)      // delaying for 2 seconds to keep JVM alive

    }
}