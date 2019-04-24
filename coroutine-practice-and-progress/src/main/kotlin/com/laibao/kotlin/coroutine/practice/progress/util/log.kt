package com.laibao.kotlin.coroutine.practice.progress.util

import java.time.*

fun log(msg: String) = println("${Instant.now()} [${Thread.currentThread().name}] $msg")