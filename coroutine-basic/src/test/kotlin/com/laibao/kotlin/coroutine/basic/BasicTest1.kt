package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

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
        assertEquals(resList, listOf("Hello,", "word!"))
    }


    suspend fun expensiveComputation(res: MutableList<String>) {
        delay(5000L)
        res.add("word!")
    }



    @Test
    fun givenHugeAmountOfCoroutines_whenStartIt_thenShouldExecuteItWithoutOutOfMemory() {

        runBlocking<Unit> {
            // given
            val counter = AtomicInteger(0)

            val numberOfCoroutines = 1000_000

            // when
            val jobs = List(numberOfCoroutines) {
                launch {
                    delay(2000L)
                    counter.incrementAndGet()
                }
            }
            jobs.forEach { it.join() }

            println("the counter value is ${counter.get()}")

            // then
            assertEquals(counter.get(), numberOfCoroutines)
        }
    }


    @Test
    fun givenCancellableJob_whenRequestForCancel_thenShouldQuit() {
        runBlocking<Unit> {
            // given
            val job = launch {
                while (isActive) {
                    println("is working")
                }
            }

            delay(1300L)

            // when
            job.cancel()

            // then cancel successfully

        }
    }


    @Test(expected = CancellationException::class)
    fun givenAsyncAction_whenDeclareTimeout_thenShouldFinishWhenTimedOut() {
        runBlocking<Unit> {
            withTimeout(1300L) {
                repeat(1000) { i ->
                    println("Some expensive computation $i ...")
                    delay(500L)
                }
            }
        }
    }


    @Test
    fun givenHaveTwoExpensiveAction_whenExecuteThemAsync_thenTheyShouldRunConcurrently() {
        runBlocking<Unit> {
            val delay = 1000L
            val time = measureTimeMillis {
                // given
                val one = async {
                    //someExpensiveComputation(delay)
                    delay(2000)
                }
                val two = async {
                    //someExpensiveComputation(delay)
                    delay(3000)
                }

                // when
                runBlocking {
                    one.await()
                    two.await()
                }
            }

            println("the time value is $time")
            // then
            assertTrue(time < delay * 5)
        }
    }


    @Test
    fun givenTwoExpensiveAction_whenExecuteThemLazy_thenTheyShouldNotConcurrently() {

        runBlocking<Unit> {
            val delay = 1000L

            val time = measureTimeMillis {
                // given
                val one = async(start= CoroutineStart.LAZY) {
                    //someExpensiveComputation(delay)
                    delay(2000)
                }
                val two = async(start = CoroutineStart.LAZY) {
                    //someExpensiveComputation(delay)
                    delay(3000)
                }


                one.start()
                two.start()

                // when
                runBlocking {
                    one.await()
                    two.await()
                }
            }

            println("the time value is $time")

            // then
            //assertTrue(time > delay * 4)
            assertTrue(time < delay * 4)
        }
    }
}