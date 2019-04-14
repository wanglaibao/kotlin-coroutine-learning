package com.laibao.kotlin.coroutine.basic

fun main() {

    val addResult = add(100){a -> a * a}

    val addResult1 = add(100){it * it * it}

    println("the result of addResult is $addResult")

    println("the result of addResult1 is $addResult1")

    val block0:()->Unit = {
        println("我是一个函数类型")
    }

    block0()

    block0.invoke()

    (1 .. 100).forEach{
        //下面这个是匿名函数也就是lambda表达式
        {
            println("我是中国人，毛主席万岁")
        }()
    }

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




    println("----------------------------------------------------")
    println(cacl(100,100,{m,n -> m + n}))
    println(cacl(100,100,{m,n -> m - n}))
    println(cacl(100,100,{m,n -> m * n}))
    println(cacl(100,100,{m,n -> m / n}))
    println("----------------------------------------------------")
}

fun add(a:Int,block: (Int) -> Int):Int{
    return block(a)
}

fun cacl(a:Int,b:Int,block:(Int,Int) -> Int):Int {
   return block.invoke(a,b)
}

fun caclute(a:Int,b:Int,block:(Int,Int) -> Int):Int {
    return block(a,b)
}