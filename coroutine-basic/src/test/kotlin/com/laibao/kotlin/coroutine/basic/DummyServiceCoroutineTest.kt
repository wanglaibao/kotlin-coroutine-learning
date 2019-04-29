package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.lang.Runnable
import java.time.Duration
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class DummyServiceCoroutineTest {

    private lateinit var services: Array<String>

    private lateinit var lambdas: List<DummyService>

    private object MyCoroutineDispatcher : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            thread {
                block.run()
            }
        }
    }

    @Before
    internal fun setUp() {
        services = arrayOf("Service A", "Service B", "Service C", "Service X", "Service Y", "Service Z")
        lambdas = services.map { DummyService(it) }

    }

    val DummyService.asyncContent: Deferred<ContentDuration>
        get() = runBlocking { async (MyCoroutineDispatcher){ content } }

    @Test
    fun should_be_parallel() {
        val start = Instant.now()
        val results = runBlocking {
            lambdas.map { it.asyncContent }
                    .map { it.await() }
        }
        val end = Instant.now()
        results.forEach { println(it) }
        assertThat(results).isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(services)
        val maxTimeElapsed = results.maxBy { it -> it.duration }?.duration?.toLong()
        println("Time taken by the longest service is  $maxTimeElapsed milliseconds")
        val duration = Duration.between(start, end)
        val timeElapsed = duration.toMillis()
        println("Time taken by the co-routine block is $timeElapsed milliseconds")
        assertThat(timeElapsed).isGreaterThanOrEqualTo(maxTimeElapsed)
    }


    @Test
    fun testValidUser() = runBlocking {

        if (isValidUser("jinge")) {
            println("我是金戈")
        }else{
            println("我是王八蛋")
        }

    }

    suspend fun isValidUser(userId:String):Boolean = withContext(Dispatchers.Default) {
        true
    }
}

