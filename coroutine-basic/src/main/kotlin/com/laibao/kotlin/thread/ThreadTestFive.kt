package com.laibao.kotlin.thread

import kotlin.concurrent.thread

fun main() {
    val thread1 = SimpleThread()

    thread1.start()

    val threadWithRunnable = Thread(SimpleRunnable())

    threadWithRunnable.start()

    val threadLambda = Thread {
        println("${Thread.currentThread().name} has run.")
    }

    threadLambda.start()

    thread{
        (1 .. 100).forEach{
            println("${Thread.currentThread().name} has run.")
        }
    }
}


/**
 *      Creating Threads
 * 1: Creating a thread in Kotlin is similar to doing so in Java
 *
 * 2: Kotlin thread() Function
 *
 *          fun thread(
 *                  start: Boolean = true,
 *                  isDaemon: Boolean = false,
 *                  contextClassLoader: ClassLoader? = null,
 *                  name: String? = null,
 *                  priority: Int = -1,
 *                  block: () -> Unit
 *                ): Thread
 *
 *
 *           The function accepts five parameters:
 *
 *                  start – To run immediately the thread
 *                  isDaemon – To create the thread as a daemon thread
 *                  contextClassLoader – A class loader to use for loading classes and resources
 *                  name – To set the name of the thread
 *                  priority – To set the priority of the thread
 *
 */

class SimpleThread: Thread(){

    override fun run() {
        println("${Thread.currentThread().name} has run.")
    }
}


class SimpleRunnable: Runnable {

    override fun run() {
        println("${Thread.currentThread().name} has run.")
    }
}