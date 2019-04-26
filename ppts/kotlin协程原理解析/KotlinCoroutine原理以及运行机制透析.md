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

*   2:  CPS(Continuation Passing Style) transform and Switch SM (State Machine)

*   3:  suspendCoroutine functions

*   4:  coroutine Builders


##  Continuation and suspending functions

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

        A:Continuation：函数参数和匿名内部类
            suspending函数编译之后薪增加Continuation类型参数,并且返回值类型变为Any?

                fun postItem(item: Item, cont: Continuation): Any?

            suspending函数编译之后新增加Continuation类型的匿名内部类

                val sm = cont as? ThisSM ?: object : ThisSM {
                  fun resume() {
                    postItem(null, this)
                  }
                }

            Continuation这个名字来源于CPS(Continuation-Passing-Style)
            CPS是一种比较特殊的Callback. Continuation中文翻译为连续体,意思就是后续的部分.
            对于requestToken函数来说,Continuation就是createPost函数和processPost函数.
            常见的CPS中,Continuation部分都会被放在回调接口中实现,这是一种编程风格.



            /**
             * Interface representing a continuation after a suspension point that returns value of type `T`.
             */
            @SinceKotlin("1.3")
            public interface Continuation<in T> {
                /**
                 * Context of the coroutine that corresponds to this continuation.
                 */
                public val context: CoroutineContext

                /**
                 * Resumes the execution of the corresponding coroutine passing successful or failed [result] as the
                 * return value of the last suspension point.
                 */
                public fun resumeWith(result: Result<T>)
            }


            /**
             * Resumes the execution of the corresponding coroutine passing [value] as the return value of the last suspension point.
             */
            @SinceKotlin("1.3")
            @InlineOnly
            public inline fun <T> Continuation<T>.resume(value: T): Unit = resumeWith(Result.success(value))


            /**
             * Resumes the execution of the corresponding coroutine so that the [exception] is re-thrown right after the
             * last suspension point.
             */
            @SinceKotlin("1.3")
            @InlineOnly
            public inline fun <T> Continuation<T>.resumeWithException(exception: Throwable): Unit = resumeWith(Result.failure(exception))


            通过上面的Continuation接口定义以及扩展函数的定义,我们可以看到resume扩展函数用来resume【恢复】suspending【暂停的】 Coroutine的执行.

            如何恢复暂停的Coroutine的执行?

            从上面的示例代码可以看到,postItem函数对应的Continuation类型的匿名内部类的resume函数会去回调postItem函数自己(但入参发生了变化,后面会解释).

            并且,在postItem函数内部调用其他suspending函数的时候会传递这个Continuation,

            后续函数可以通过Continuation重新回调postItem函数.

            小结：每个suspending函数编译后会增加一个Continuation类型的参数.
                 每个suspending函数都有一个回调自己的Continuation实现类,
                 并且这个类会被传递给这个suspending函数所调用的其它suspending函数,
                 这些子函数可以通过Continuation回调父函数以恢复暂停的程序。

        B:Switch状态机


        C:Continuation的父子调用


        D:小结


    ```




##  CPS(Continuation Passing Style) transform and Switch SM (State Machine)


##  suspendCoroutine functions


##  coroutine Builders











