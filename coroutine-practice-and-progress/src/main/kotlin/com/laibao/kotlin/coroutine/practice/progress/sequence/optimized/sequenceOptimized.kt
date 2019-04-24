package com.laibao.kotlin.coroutine.practice.progress.sequence.optimized

import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*
import com.laibao.kotlin.coroutine.practice.progress.sequence.*


fun <T> sequence(block: suspend SequenceScope<T>.() -> Unit): Sequence<T> = Sequence {
    SequenceCoroutine<T>().apply {
        nextStep = block.createCoroutineUnintercepted(receiver = this, completion = this)
    }
}

class SequenceCoroutine<T>: AbstractIterator<T>(), SequenceScope<T>, Continuation<Unit> {
    lateinit var nextStep: Continuation<Unit>

    // AbstractIterator implementation
    override fun computeNext() { nextStep.resume(Unit) }

    // Completion continuation implementation
    override val context: CoroutineContext get() = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
        done()
    }

    // Generator implementation
    override suspend fun yield(value: T) {
        setNext(value)
        return suspendCoroutineUninterceptedOrReturn { cont ->
            nextStep = cont
            COROUTINE_SUSPENDED
        }
    }
}