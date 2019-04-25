package com.laibao.kotlin.coroutine.practice.progress.context

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext


class ThreadContext(
        nThreads: Int,
        name: String
) : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor{


    val threadNo = AtomicInteger()

    val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(nThreads) { target ->


                                                         thread(start = false,
                                                                isDaemon = true,
                                                                name = name + "-" + threadNo.incrementAndGet()) {
                                                             target.run()
                                                         }
    }


    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            ThreadContinuation(continuation.context.fold(continuation) { cont, element ->
                if (element != this@ThreadContext && element is ContinuationInterceptor)
                    element.interceptContinuation(cont) else cont
            })


    private inner class ThreadContinuation<T>(val cont: Continuation<T>) : Continuation<T>{
        override val context: CoroutineContext = cont.context

        override fun resumeWith(result: Result<T>) {
            executor.execute { cont.resumeWith(result) }
        }
    }
}
















