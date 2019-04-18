package com.laibao.kotlin.coroutine.basic

import junit.framework.Assert
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



    @Test
    fun testMySuspending() = runBlocking<Unit> {

        println("--------------")
        println("--------------")
        println("--------------")
        println("--------------")
        println("--------------")
        println("--------------")
    }



    @Test
    fun givenAsyncCoroutine_whenStartIt_thenShouldExecuteItInTheAsyncWay() = runBlocking{

        // given
        val resList = mutableListOf<String>()

        // when
//        runBlocking<Unit> {
//            val promise = launch() {
//                expensiveComputation(res)
//            }
//            res.add("Hello,")
//            promise.join()
//        }

        val promise = launch() {
            expensiveComputation(resList)
        }

        resList.add("Hello,")

        promise.join()

        resList.forEach{ println(it)}

        // then
        Assert.assertEquals(resList, listOf("Hello,", "word!"))
    }


    suspend fun expensiveComputation(res: MutableList<String>) {
        delay(5000L)
        res.add("word!")
    }
}