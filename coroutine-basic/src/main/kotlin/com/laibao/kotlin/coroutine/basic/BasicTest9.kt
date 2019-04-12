package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    repeat(5000_000) { // launch a lot of coroutines
        launch {
            delay(5000L)
            println("--------------------")
            println("我是中国人")
            println("--------------------")
        }
    }
}