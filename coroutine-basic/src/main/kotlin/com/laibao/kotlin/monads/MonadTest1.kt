package com.laibao.kotlin.monads

fun main() {
    println(thunk(1000))

    println()

    println(thunk1(200000))



    ThunkProgram.func {
        println("thunk invoked")
        12
    }


}

/**
 *  Thunk 函数的含义
 *
 *  编译器的"传名调用"实现,往往是将参数放到一个匿名函数【或者 lambda expression {} 】之中，
 *
 *  再将这个匿名函数【或者 lambda expression {} 】传入函数体。这个匿名函数【或者 lambda expression {} 】就叫做Thunk函数
 *
 *  function type :: (type) -> type
 *
 */


/**
 * Thunks have two key differences from lambdas:
 *
 *      1: A thunk is invoked when the name is used
 *
 *      2: A thunk is invoked every time is name is used
 *
 */


val thunk:(Int) -> Int = fun(x:Int):Int{
    return x + 5
}


val thunk1:(Int) -> Int = {x -> x + 5}



object ThunkProgram {

    fun func(thunk:()-> Int):Unit {
        println(thunk())
        println(thunk() * 2)
        println(thunk() * 3)
    }


}

