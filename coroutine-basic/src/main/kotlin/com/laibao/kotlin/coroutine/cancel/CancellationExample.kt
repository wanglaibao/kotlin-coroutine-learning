package com.laibao.kotlin.coroutine.cancel

import kotlinx.coroutines.*

fun main() = runBlocking {

    val outer = launch {

        try {
            launch(coroutineContext) {
                try {
                    // work here
                    println("wait inner")
                    delay(50000)
                } catch (ex: CancellationException) {
                    println("In inner catch:CancellationException")
                }
                println("inner done")
            }

            println("wait outer")

            try {
                delay(4000)
            } catch (ex: CancellationException) {
                println("In delay catch:CancellationException")
            }

        } catch (ex: CancellationException) {
            println("In outer catch:CancellationException")
        }

    }

    delay(3000) // give it  a chance to run
    outer.cancelChildren()
    println(outer.isCancelled)
    println(outer.isCompleted)
    outer.join()
    println(outer.isCompleted)

}