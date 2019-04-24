package com.laibao.kotlin.coroutine.practice.progress.run

import java.util.concurrent.locks.ReentrantLock
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

private class BlockingCoroutine<T>(override val context: CoroutineContext) : Continuation<T> {
    private val lock = ReentrantLock()
    private val done = lock.newCondition()
    private var result: Result<T>? = null

    private inline fun <T> locked(block: () -> T): T {
        lock.lock()
        return try {
            block()
        } finally {
            lock.unlock()
        }
    }

    private inline fun loop(block: () -> Unit): Nothing {
        while (true) {
            block()
        }
    }

    override fun resumeWith(result: Result<T>) = locked {
        this.result = result
        done.signal()
    }

    fun getValue(): T = locked<T> {
        loop {
            val result = this.result
            if (result == null) {
                done.awaitUninterruptibly()
            } else {
                return@locked result.getOrThrow()
            }
        }
    }
}


fun <T> runBlocking(context: CoroutineContext, block: suspend () -> T): T =
        BlockingCoroutine<T>(context).also { block.startCoroutine(it) }.getValue()