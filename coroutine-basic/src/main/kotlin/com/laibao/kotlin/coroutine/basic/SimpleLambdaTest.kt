package com.laibao.kotlin.coroutine.basic

fun main() {
    val result = {a:Int,b:Int -> a + b}
    println(result(100,200))
    println(result.invoke(200,300))

    val result1 = {a:Int,b:Int,c:Int -> a + b + c}.invoke(1000,2000,3000)
    println(result1)

    val testResult:Unit = {
                        println("金戈")
                      }.invoke()


    /**
     * 保存lambda表达式
     */
    val block:() -> Unit = {
        println("我是金戈啊，我是个大坏蛋")
    }

    /**
     * 下面两个是调用lambda表达式
     */
    block()
    block.invoke()

    val block1:(() -> Unit)? = null
    block1?.invoke()
}