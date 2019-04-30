package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class CoroutineBasicsTest {

    /**
     *  delay() is a special suspending function that does not block a thread,
     *  but suspends coroutine and it can be only used from a coroutine or in some other suspend function
     */
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


    @Test
    fun testGlobalScope2() {
        val job = GlobalScope.launch {
            delay(3000L)
            println("World!")
        }

        println("Hello,")
        //TimeUnit.SECONDS.sleep(2)
        runBlocking {
            delay(5000)
        }
    }


    @Test
    fun testThreadWay() {
        // You can achieve the same result replacing GlobalScope.launch { ... }
        // with thread { ... } and delay(...) with Thread.sleep(...). Try it.
        thread {
            TimeUnit.SECONDS.sleep(2)
            println("World!")
        }
        println("Hello,")
        TimeUnit.SECONDS.sleep(4)
    }


    /**
     * Global coroutines are like daemon threads
     */
    @Test
    fun testGlobalScope5() = runBlocking<Unit> {
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


    /**
     * There is still something to be desired for practical usage of coroutines.
     * When we use GlobalScope.launch we create a top-level coroutine.
     * Even though it is light-weight, it still consumes some memory resources while it runs
     *
     *
     * There is a better solution.
     * We can use structured concurrency in our code.
     * Instead of launching coroutines in the GlobalScope,
     * just like we usually do with threads (threads are always global),
     * we can launch coroutines in the specific scope of the operation we are performing.
     *
     * Every coroutine builder, including runBlocking,
     * adds an instance of CoroutineScope to the scope of its code block.
     * We can launch coroutines in this scope without having to join them explicitly,
     * because an outer coroutine (runBlocking ) does not complete until
     * all the coroutines launched in its scope complete
     */

    @Test
    fun testStructuredConcurrency() = runBlocking{

        launch { // launch new coroutine in the scope of runBlocking
            delay(10000L)
            println("World!")
        }

        println("Hello,")
    }


    @Test
    fun testCoroutinesAreLightWeight() = runBlocking{

        repeat(3000_000) { // launch a lot of coroutines
            launch {
                delay(3000L)
                println("-------------")
            }
        }
    }

    @Test
    fun testExtractFunctionRefactoring() = runBlocking{

        launch {
            doWorld()
        }
        println("Hello,")
    }


    /**
     * Suspending functions can be used inside coroutines just like regular functions,
     * but their additional feature is that they can, in turn, use other suspending functions,
     * like delay in this example, to suspend execution of a coroutine.
     */
    // this is your first suspending function
    private suspend fun doWorld() {
        delay(10000L)
        println("World!")
    }


    /**
     * In addition to the coroutine scope provided by different builders,
     * it is possible to declare our own scope using coroutineScope builder.
     * It creates new coroutine scope and does not complete until all launched children complete.
     * The main difference between runBlocking and coroutineScope is that
     * the latter does not block the current thread while waiting for all children to complete.
     */
    @Test
    fun testScopeBuilder() = runBlocking{

        launch {
            delay(2000L)
            println("Task from runBlocking_2222222222222")
        }


        coroutineScope { // Creates a new coroutine scope
            launch {
                delay(5000L)
                println("Task from nested launch_333333333333")
            }

            delay(1000L)
            println("Task from coroutine_111111111 scope") // This line will be printed before nested launch
        }


        println("Coroutine_44444444444444 scope is over") // This line is not printed until nested launch completes
    }


    @Test
    fun testFirstCoroutine() = runBlocking{

        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("Hello,") // main thread continues while coroutine is delayed
        Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive

    }
}