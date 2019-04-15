package com.laibao.kotlin.thread

import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main() {

    println("asdfasdfasfd00000000000")
    val counter = AtomicInteger()
    try {
        val list = ArrayList<Thread>()

        for (i in 0..10000) {
            val thread = thread(start = false) {
               println("${Thread.currentThread().name} 开始启动了")
                counter.incrementAndGet()
                Thread.sleep(1000)
            }

            list.add(thread)
        }

        list.forEach{
            it.start()
            it.join()
        }
    } catch (oome: OutOfMemoryError) {
        println("Spawned ${counter.get()} threads before crashing")
        System.exit(-42)
    }

    println("asdfasdfasfa1111111111")

}