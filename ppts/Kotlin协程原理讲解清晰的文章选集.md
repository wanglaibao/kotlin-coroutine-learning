#                                   Kotlin协程原理选集
*   [Coroutines in Kotlin 多看几遍] (http://blog.alexnesterov.com/post/coroutines/)

*   [Kotlin coroutines and Spring 5] (http://blog.alexnesterov.com/post/kotlin-coroutines-and-spring-5/)

*   [kotlinx.coroutines/coroutines-guide.md] (https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md)

*   [kotlinx.coroutines/docs/coroutines-guide.md] (https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/coroutines-guide.md)

*   [Kotlin Coroutine context and dispatchers] (https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md#coroutine-context-and-dispatchers)

*   [Kotlin CoroutineDispatcher Javadoc] (https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/index.html)
*
*

### What about Futures for non-blocking calls

* Java Future and CompletableFuture

* Java NIO with non-blocking I/O API

* Reactive Streams and RxJava2 and Reactor

##  Java的线程技术栈的困惑
```
    传统的Java应用提高并发的主要方式就是开启更多的线程,但线程太多会导致资源的浪费，太低又容易导致并发不够.

    虽然如 Netty 这样的技术能解决IO密集场景下的并发问题，但是使用门槛比较高，学习曲线比较陡，不易于大面积使用.

    而Kotlin的Coroutine特性为 JVM 上的高并发应用开发带了一个非常有希望的新选择

    备注: Kotlin Coroutine虽然在形式上简化了异步代码的开发,但也对使用者提出了相当的要求.

    如果对Java并发编程,NIO,Netty,Reactivce【反应式】编程,以及Kotlin技术栈本身等技术缺乏足够的了解,

    那恐怕还是难以顺畅使用Kotlin Coroutine来进行异步化编程.
```

##  What is the problem that Coroutine trying to solve

```
    coroutine is a mechanism【机制】 to optimise【优化】

    and then execute a function (subroutine) in non-preemptive【先发制人的】 multi-tasking way.

    coroutine is trying to solve the problem of effectively utilising resources (thread) using cooperative multitasking.

```


### Kotlin Coroutine 有关的异步并发库

*   核心模块 kotlinx-coroutines-core

*   kotlinx-coroutines-jdk8

*   kotlinx-coroutines-reactive

*   kotlinx-coroutines-reactor

*   kotlinx-coroutines-rx2

*   kotlinx-coroutines-rx1

### Coroutine Builder

*   runBlocking{}

        ```
            这个Coroutine Builder的作用是阻塞调用它的线程
        ```

*   launch{}

        ```
            这个Coroutine Builder的作用是创建一个Coroutine并执行它，
            并返回一个Job对象，用于控制这个Coroutine的执行但没有结果的返回.
            Job对象提供了cancel(),join()等方法，用来控制Job的执行.
        ```

*   async{}

        ```
            与launch类似，async的作用也是创建一个Coroutine并执行它。
            不同的是launch返回的Job，其只能控制Coroutine的执行,但是不能得到任何返回结果.
            async返回的是Deferred<T>,可以通过调用其提供的await()方法得到Coroutine的执行结果.

        ```

*   配合Spring Reactor 使用的mono{}

    ```
            mono与前几个Coroutine Builder有所不同.
            最明显的区别是在后面的括号了带了一个Unconfined.
            简单说,这个Unconfined是一个CoroutineDispatcher,用来限定使用什么线程来执行Coroutine.
            下同
    ```

*   配合Spring Reactor 使用的flux{}


### 所有Coroutine Builder方法参数补充说明

```
    所有的Coroutine Builder方法的第一参数都是CoroutineContext.
    那为什么可以把CoroutineDispatcher作为参数传给Coroutine Builder呢?

        原来CoroutineDispatcher实现了CoroutineContext.Element接口,而Element又是一个特殊的CoroutineContext,其是只存放了一个元素的CoroutineContext.
        所以,CoroutineDispatcher也是一个CoroutineContext.
        这个CoroutineContext仅包含一个元素,而这个元素就是CoroutineDispatcher自己.


    当Coroutine执行的时候,Kotlin会看当前Coroutine的CoroutineContext里面是否有CoroutineDispatcher,
    如果有,则使用CoroutineDispatcher来限定Coroutine所使用的线程.


    如果没有给Coroutine Builder设置参数时,launch{},async{}以 mono{}和flux{}
    默认使用的CoroutineDispatcher是CommonPool,一个公共的线程池实现【ForkJoinThreadPool】.
    runBlocking{}默认使用的是BlockingEventLoop.
    另一个常见的CoroutineDispatcher实现就是mono{}中的Unconfined.

    Unconfined意思就是不限定,在第一个暂停点之前,Coroutine的执行线程都是调用的线程.
    在第一个暂停点之后,用哪个线程执行就是由suspend function方法决定了.

```


### suspend functions
*   delay()
*   await()
*   awaitSingle()






