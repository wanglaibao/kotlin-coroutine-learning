package com.laibao.kotlin.coroutine.practice.progress.context

import com.laibao.kotlin.coroutine.practice.progress.future.future
import com.laibao.kotlin.coroutine.practice.progress.util.log

fun main() {
    future(Swing) {
        log("Let's Swing.delay for 1 second")
        Swing.delay(1000)
        log("We're still in Swing EDT")
    }
}