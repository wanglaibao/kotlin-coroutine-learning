package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * kotlin-samples : https://github.com/s1monw1/kotlin-samples
 */
fun main() = runBlocking {

    coroutineScope {

        val outerLaunch = launch {

            launch {
                while (true) {
                    delay(3000)
                    println("Hello from first inner launch")
                }
            }

            launch {
                while (true) {
                    delay(3000)
                    println("Hello from second inner launch")
                }
            }
        }

        println("Hello from runBlocking after outer launch")

        delay(8000)

        outerLaunch.cancel()
    }

    println("finished coroutineScope")
}