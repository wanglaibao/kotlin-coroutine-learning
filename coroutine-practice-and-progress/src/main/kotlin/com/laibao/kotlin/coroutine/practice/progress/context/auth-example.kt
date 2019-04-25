package com.laibao.kotlin.coroutine.practice.progress.context

import com.laibao.kotlin.coroutine.practice.progress.run.runBlocking
import kotlin.coroutines.coroutineContext


fun main(args: Array<String>) {
    runBlocking(AuthUser("admin")) {
        doSomething()
    }
}

suspend fun doSomething() {
    val currentUser = coroutineContext[AuthUser]?.name ?: throw SecurityException("unauthorized")
    println("Current user is $currentUser")
}
