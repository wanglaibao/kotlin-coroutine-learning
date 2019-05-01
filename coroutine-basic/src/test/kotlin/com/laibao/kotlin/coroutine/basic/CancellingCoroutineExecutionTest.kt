package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import org.junit.Test

class CancellingCoroutineExecutionTest {

    @Test
    fun testCancellingCoroutine() = runBlocking{
        val job = launch {
            repeat(10000) { i ->
                println("I'm sleeping $i ...")
                delay(5000L)
            }
        }
        delay(13000L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        job.join() // waits for job's completion
        println("main: Now I can quit.")
    }


    @Test
    fun testCancelAndJoin() = runBlocking{
        val job = launch (Dispatchers.Default) {
            while (isActive) {
                //repeat(10000) { i ->
                    println("I'm sleeping  ...")
                    //delay(5000L)
                //}
            }
        }
        delay(300L) // delay a bit
        job.cancelAndJoin()
        println("main: I'm tired of waiting!")
        println("main: Now I can quit.")
    }


    @Test
    fun testCancellationCooperativeOne() = runBlocking{
        val startTime = System.currentTimeMillis()
        println(" 1111111 ${Thread.currentThread().name}")
        val job = launch (Dispatchers.Default){
            println(" 2222222 ${Thread.currentThread().name}")
            var nextPrintTime = startTime
            var i = 0
            while (isActive) {// computation loop, just wastes CPU
                // println a message twice a second
                //delay(500)
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        println(" 33333333 ${Thread.currentThread().name}")
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }


    @Test
    fun testCancellationCooperativeTwo() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
            // println a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    @Test
    fun testCancellationCooperativeThree() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // computation loop, just wastes CPU
                // println a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}