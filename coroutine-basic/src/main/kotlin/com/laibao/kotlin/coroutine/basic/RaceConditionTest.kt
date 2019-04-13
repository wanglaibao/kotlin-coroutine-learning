package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

data class UserInfo2(val name: String, val lastName: String, val id: Int)

lateinit var user: UserInfo2

/**
 * This will crash because the information is not ready by the
 * time we try to use it.
 */

fun main() = runBlocking {
    asyncGetUserInfo(1)
    // Do some other operations
    delay(1000)

    println("User ${user.id} is ${user.name}")
}

suspend fun asyncGetUserInfo(id: Int) {
    delay(1100)
    user = UserInfo2(id = id, name = "Susan", lastName = "Calvin")
}