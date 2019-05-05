package com.laibao.kotlin.coroutine.context

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun log1(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() {
//sampleStart
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log1("Started in ctx1")
                withContext(ctx2) {
                    log1("Working in ctx2")
                }
                log1("Back to ctx1")
            }
        }
    }
//sampleEnd
}