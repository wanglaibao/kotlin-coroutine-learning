package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import org.junit.Test


class DemystifyingKotlinCoroutinesTest {

    val singleThreadDispatcher = newSingleThreadContext("singleThreadDispatcher")

    /**
     *  1:
     *
     * launch takes CoroutineContext as first parameter which then defaults to DefaultDispatcher
     *
     * which is a CommonPool class which creates a CoroutineContext with Executors based on total number of processors in device.
     *
     * Coroutine Builders like launch can accept a CoroutineDispatcher which is actually responsible for running code in separate thread.
     *
     *
     *
     * We can create our own dispatchers easily.

     * val singleThreadDispatcher = newSingleThreadContext("singleThreadDispatcher")
     *
     * newSingleThreadContext is provided by Kotlin coroutines library itself to create a context which will run on just single thread. We can create our own function on top of this
     *
     */

    // job = singleThreadAsync { myHeavyFunction() }

    fun singleThreadAsync(block: suspend () -> Unit): Job = runBlocking{

        launch(singleThreadDispatcher) { block.invoke() }

    }


    //   public actual val Default: CoroutineDispatcher = createDefaultDispatcher()

    @Test
     fun myHeavyFunction() = runBlocking<Unit>{

        val job = launch {
            println("Thread Running and name is ${Thread.currentThread().name}")
        }

        singleThreadAsync {
            println("Thread Running and name is ${Thread.currentThread().name}")
            delay(3000)
            println("Thread Running and name is ${Thread.currentThread().name}")
        }

    }


    /**
     * Let’s see what we can do more with Dispatchers
     */

}