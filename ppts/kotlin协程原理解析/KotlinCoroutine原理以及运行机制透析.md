# Kotlin Coroutine 原理以及运行机制透析

## 示例代码

```
    suspend fun postItem(item: Item): PostResult {
        val token = requestToken()
        val post = createPost(token, item)
        val postResult = processPost(post)
        return postResult
    }

```

## Kotlin Coroutine 核心原理

*   1:  Continuation and suspending functions

    ```
        Kotlin编译器会对suspending functions 进行特殊处理,对代码进行转换,从而实现suspending机制。
        Kotlin编译器主要做了下面这三项处理：
            1:  在挂起函数参数列表中添加Continuation类型入参作为参数列表的最后一个参数,并且返回值变为Any?
            2:  在函数内部创建Continuation类型的匿名内部类作为val sm变量
            3:  在函数内部对其他suspending函数的调用变为switch形式的状态机

        接下来详细介绍一下这三项处理

        编译器处理后的函数 postItem

        fun postItem(item: Item, cont: Continuation): Any? {

          // 编译之后新增加的Continuation类型的匿名内部类
          val sm = cont as? ThisSM ?: object : ThisSM {
            fun resume(…) {
              postItem(null, this)
            }
          }

          switch (sm.label) {
            case 0:
              sm.item = item
              sm.label = 1
              return requestToken(sm)
            case 1:
              val item = sm.item
              val token = sm.result as Token
              sm.label = 2
              return createPost(token, item, sm)
            case 2:
              val post = sm.result as Post
              sm.label = 3
              return processPost(post, sm)
            case 3:
              return sm.result as PostResult
        }

        下面对编译之后的方法进行必要的说明以方便理解

        A:Continuation：方法参数和匿名内部类



        B:Switch状态机


        C:Continuation的父子调用


        D:小结


    ```




*   2:  CPS(Continuation Passing Style) transform and Switch SM (State Machine)

*   3:  suspendCoroutine functions

*   4:  coroutine Builders


##  Continuation and suspending functions



##  CPS(Continuation Passing Style) transform and Switch SM (State Machine)


##  suspendCoroutine functions


##  coroutine Builders











