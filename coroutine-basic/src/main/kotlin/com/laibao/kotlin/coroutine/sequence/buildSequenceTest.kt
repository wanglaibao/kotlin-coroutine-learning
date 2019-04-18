package com.laibao.kotlin.coroutine.sequence

import kotlinx.coroutines.delay

fun main() {
    fibonacciSeq.take(50).iterator().forEach { println(it)}
}

val fibonacciSeq = sequence{
    var a = 0
    var b = 1
    yield(1)
    while (true) {
        yield(a + b)
        val tmp = a + b
        a = b
        b = tmp
    }
}


suspend fun expensiveComputation(res: MutableList<String>) {
    delay(5000L)
    res.add("word!")
}