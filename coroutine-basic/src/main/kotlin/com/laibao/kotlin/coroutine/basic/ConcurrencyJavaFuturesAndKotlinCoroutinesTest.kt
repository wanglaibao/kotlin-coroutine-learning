package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.security.SecureRandom
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

fun main() {


    val callables: List<Callable<ContentDuration>> = arrayOf("Service A", "Service B", "Service C")
            .map {  DummyService(it) }
            .map { Callable<ContentDuration> { it.content } }

    val executor = Executors.newWorkStealingPool()

    val results = executor.invokeAll(callables).map { it.get() }

    /*
    var results1 = runBlocking {
        arrayOf("Service A", "Service B", "Service C")
                .map { DummyService(it) }
                .map { it.asyncContent }
                .map { it.await() }
    }*/
}

class DummyService(private val name: String) {

    private val random = SecureRandom()

    val content: ContentDuration
        get() {
            val duration = random.nextInt(5000)
            Thread.sleep(duration.toLong())
            return ContentDuration(name, duration)
        }
}

data class ContentDuration(val content: String, val duration: Int)

object MyCoroutineDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        thread {
            block.run()
        }
    }
}

val DummyService.asyncContent: Deferred<ContentDuration>
    get() = runBlocking {
        async { content }
    }