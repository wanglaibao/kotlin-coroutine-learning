package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * t’s tempting to think that spawning more threads can help us execute more tasks concurrently. Unfortunately, that’s not always true.
 *
 *Creating too many threads can actually make an application underperform in some situations; threads are objects which impose overhead during object allocation and garbage collection.
 *
 *To overcome these issues, Kotlin introduced a new way of writing asynchronous, non-blocking code; the Coroutine.
 *
 *Similar to threads, coroutines can run in concurrently, wait for, and communicate with each other with the difference that creating them is way cheaper than threads.
 */




/**
 * When we use Dispatchers.Default or GlobalScope.launch we create a top-level coroutine. Even though it is light-weight, it still consumes some memory resources while it runs.
 *
 * Instead of launching coroutines in the GlobalScope, just like we usually do with threads (threads are always global), we can launch coroutines in the specific scope of the operation we are performing:
 */
fun main() {

    runBlocking {
        val job = launch(Dispatchers.Default) {
            println("${Thread.currentThread()} has run.")
        }
    }

    val job = GlobalScope.launch {
        println("${Thread.currentThread()} has run.")
    }


    /**
     * In this case, we start new coroutine inside the runBlocking coroutine builder
     * (which we’ll describe later) without specifying the context.
     * Thus, the coroutine will inherit runBlocking‘s context.
     */
    runBlocking {
        val job = launch {
            println("${Thread.currentThread()} has run.")
        }
    }
}