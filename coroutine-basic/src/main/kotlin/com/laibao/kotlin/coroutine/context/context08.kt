package com.laibao.kotlin.coroutine.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun log2(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking(CoroutineName("main")) {

//sampleStart
    log2("Started main coroutine")

    // run two background value computations
    val v1 = async(CoroutineName("v1 coroutine")) {

        delay(500)

        log("Computing v1")

        252
    }

    val v2 = async(CoroutineName("v2 coroutine")) {

        delay(1000)

        log("Computing v2")

        6
    }

    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
//sampleEnd

}