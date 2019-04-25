package com.laibao.kotlin.coroutine.basic

class User

class Account

suspend fun getUserFromNetwork(userId : Int) : User {
    return User()
}

suspend fun getUserAccountInfo(accountId : Int) : Account {
    return Account()
}