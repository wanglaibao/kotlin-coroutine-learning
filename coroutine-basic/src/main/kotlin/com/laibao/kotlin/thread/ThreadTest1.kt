package com.laibao.kotlin.thread

import kotlin.concurrent.thread

fun main() {
    println("${Thread.currentThread().name} 开始执行了")

    val thread1:Thread = thread (name = "thread_1"){
        println("${Thread.currentThread().name} 开始执行了")
        for (i in 1..1000) {
            println("T1: $i")
        }
    }


    val thread2:Thread = thread (name = "thread_2"){
        println("${Thread.currentThread().name} 开始执行了")
        for (i in 1..1000) {
            println("T2: $i")
        }
    }

    println("${Thread.currentThread().name} 执行结束了")
}