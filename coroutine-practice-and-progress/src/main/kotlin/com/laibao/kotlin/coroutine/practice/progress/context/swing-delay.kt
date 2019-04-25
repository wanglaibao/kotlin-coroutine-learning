package com.laibao.kotlin.coroutine.practice.progress.context

import javax.swing.*
import kotlin.coroutines.*

suspend fun Swing.delay(millis: Int): Unit = suspendCoroutine { cont ->
    Timer(millis) { cont.resume(Unit) }.apply {
        isRepeats = false
        start()
    }
}