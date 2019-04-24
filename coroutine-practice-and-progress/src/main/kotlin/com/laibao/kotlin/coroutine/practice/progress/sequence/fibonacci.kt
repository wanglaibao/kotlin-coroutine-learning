package com.laibao.kotlin.coroutine.practice.progress.sequence

fun main() {
    val fibonacciNumber1 = fibonacci.take(10).iterator().forEach { it -> println(it) }
}

val fibonacci = sequence {

    yield(1) // first Fibonacci number

    var cur = 1

    var next = 1

    while (true) {
        yield(next) // next Fibonacci number
        val tmp = cur + next
        cur = next
        next = tmp
    }
}