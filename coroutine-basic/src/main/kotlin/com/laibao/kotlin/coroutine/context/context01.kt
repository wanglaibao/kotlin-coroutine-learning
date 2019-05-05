package com.laibao.kotlin.coroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

//sampleStart
    launch { // context of the parent, main runBlocking coroutine
        println(" 1111111111  main runBlocking : I'm working in thread ${Thread.currentThread().name}")
    }

    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println(" 222222222 Unconfined : I'm working in thread ${Thread.currentThread().name}")
    }

    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println(" 333333333 Default : I'm working in thread ${Thread.currentThread().name}")
    }

    launch(Dispatchers.IO) { // will get dispatched to DefaultDispatcher
        println(" 44444444 IO : I'm working in thread ${Thread.currentThread().name}")
    }

    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
        println(" 55555555 newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }

    launch(newFixedThreadPoolContext(5,"MyOwnThread_")) { // will get its own new thread
        println(" 66666666 newFixedThreadPoolContext: I'm working in thread ${Thread.currentThread().name}")
    }

//sampleEnd
}