package com.laibao.kotlin.coroutine.practice.progress.sequence.optimized

fun main() {
    println(fibonacci)
    println(fibonacci.take(30).joinToString())
}

val fibonacci: Sequence<Int> = sequence {
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