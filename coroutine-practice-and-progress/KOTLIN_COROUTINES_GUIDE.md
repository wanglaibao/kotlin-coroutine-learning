# KOTLIN COROUTINES GUIDE


### The Concept of Kotlin Coroutines  【What are Coroutines: 什么是协程】

### what Coroutines look like 【协程长什么样子】

### how Coroutines work 【协程如何工作】


```
  In kotlin coroutines,async and await are not keywords but functions.

  This design allows the integration of different asynchronous APIs: futures/promises, callback-passing, etc.

  It is also general enough to express lazy generators (yield) and cover some other use cases
```


### Java Concurrency vs. Kotlin Coroutines 【线程池  VS 协程】

```
    Although Java’s concurrency tooling is well-engineered,
    it’s often difficult to utilize and fairly tedious to use.
    Another problem is that Java doesn’t directly encourage non-blocking programming.
    You often find yourself starting threads without having in mind that they’re costly and introduce blocking computations quickly
    (due to locks, sleeps, waits, etc.).
    Applying non-blocking patterns alternatively is really hard and error-prone.


    Kotlin Coroutines, on the other hand, are intended to be a lot easier and look like sequential code by hiding most of the complicated stuff from the developers.
    They provide a way to run asynchronous code without having to block threads, which offers new possibilities for applications.
    Instead of blocking threads, computations are being suspended.

    Compared to threads, coroutines are mostly very cheap in their creation,
    and the overhead that naturally comes with threads isn’t around.

    One reason is that they’re not directly mapped to native threads.
    As you will see, coroutines are executed in thread pools that are mainly managed by the library.
    Another critical difference is “limitation”: Threads are very limited because they rely on available native threads,
    coroutines on the other side are almost free, and thousands can be started at once (although this depends on what they compute).
```























