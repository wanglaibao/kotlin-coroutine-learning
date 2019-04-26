package com.laibao.kotlin.coroutine.`in`.practice.chapter1

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

data class UserInfo1(val name: String, val lastName: String, val id: Int)

lateinit var user: UserInfo1

/**
 * This will crash because the information is not ready by the
 * time we try to use it.
 */

fun main() = runBlocking {

    async {
        asyncGetUserInfo(1)

    }

    // Do some other operations
    delay(1000)

    println("User ${user.id} is ${user.name}")
}

fun asyncGetUserInfo(id: Int) {
    user = UserInfo1(id = id, name = "Susan", lastName = "Calvin")
}