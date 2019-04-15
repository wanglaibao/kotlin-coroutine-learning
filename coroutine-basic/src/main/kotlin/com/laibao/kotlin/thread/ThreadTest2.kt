package com.laibao.kotlin.thread

import kotlin.concurrent.thread

fun main() {

    val thread1 = thread(start = false,name = "thread_1") {
        for (i in 1..100) {
            println("T2: $i")
        }
    }


    val thread2 = thread(start = false,name = "thread_2") {
        for (i in 1..100) {
            println("T2: $i")
        }
    }


    thread1.start()

    thread2.start()
}