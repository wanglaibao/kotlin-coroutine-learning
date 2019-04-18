package com.laibao.kotlin.thread


fun main() {
        fibonacci.take(10).iterator().forEach { println("$it") }
}


val fibonacci =  sequence {
    yield(1) // the first fibonacci number
    var cur = 1
    var next = 1
    while (true) {
        yield(next)
        val temNumber = cur + next
        cur = next
        next = temNumber
    }
}