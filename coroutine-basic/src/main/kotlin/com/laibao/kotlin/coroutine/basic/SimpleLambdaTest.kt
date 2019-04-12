package com.laibao.kotlin.coroutine.basic

fun main() {
    val result = {a:Int,b:Int -> a + b}
    println(result(100,200))
    println(result.invoke(200,300))

    val result1 = {a:Int,b:Int,c:Int -> a + b + c}.invoke(1000,2000,3000)
    println(result1)
}