#                       How Kotlin Coroutine Works Under The Hood
```
                Theory without practice is empty;

                practice without theory is blind.
```

```
    Kotlin Coroutine is a great and powerful language feature,

    Coroutines are a new way of writing asynchronous, non-blocking code

    that’s why I think introduce coroutines is necessary
```

### Brief Intro On Kotlin Coroutine

```
Kotlin coroutine is a way of doing things asynchronously in a sequential manner.

Coroutines are a new way of writing asynchronous, non-blocking code.

Creating a coroutine is a lot cheaper vs creating a thread.

        “In Kotlin Coroutine Structure Of [Imperative], Synchronous Code is the same as Asynchronous Code.”


Coroutine are light-weight threads.

A light weight thread means it doesn’t map on native thread,

so it doesn’t require context switching on processor, so they are faster.                                                                                    By Venkat Subramariam


```

```
    Coroutines are available in many languages, there are two types of Coroutines:

            1: Stackless

            2: Stackful

    Kotlin implements stackless coroutines — it’s mean that the coroutines don’t have own stack, so they don’t map on native thread.

    Coroutines and the threads both are multitasking.

    But the difference is that threads are managed by the OS and coroutines by the users.

                One can think of a coroutine as a light-weight thread.
                Like threads, coroutines can run in parallel, wait for each other and communicate.
                The biggest difference is that coroutines are very cheap, almost free:
                we can create thousands of them, and pay very little in terms of performance.
                True threads, on the other hand, are expensive to start and keep around.
                A thousand threads can be a serious challenge for a modern machine.



    In Kotlin,async and await are not  keywords, but merely functions.

    This design allows the integration of different asynchronous APIs: futures/promises, callback-passing, etc.

    It is also general enough to express lazy generators (yield) and cover some other use cases.
```

### The anatomy of a Kotlin Coroutine 【协程剖析】

```
    It’s important that we take a quick look at four Coroutine concepts here:
```

*   CoroutineScope      【协程范围】

    ```
        our Coroutine needs CoroutineContext to run, which is provided by the interface--- CoroutineScope.

        This way we can restrict the scope of our Coroutine.
    ```

*   CoroutineContext    【协程上下文】

    ```
        CoroutineContext, as the name indicates, defines the context in which our Coroutine runs.

        CoroutineContext is used to manage lifecycle-related operations, proper threading, debugging, and handling exceptions.
    ```

*   Coroutine Builder   【协程构建者】

    ```
        Coroutine Builders are extension functions on our CoroutineScope that let we build our Coroutine and manage its execution.

        In Kotlin, we have a bunch of Coroutine Builders.

        The two most used Coroutine Buillders are launch and async.

    ```

*   CoroutineDispatcher 【协程分发器||这个十分重要对协程的理解】

    ```
        CoroutineDispatcher is an abstract class that is responsible for launching our Coroutines.
        There are 4 Dispatchers that a CoroutineDispatcher can use to launch our Coroutine:
    ```
    ```
        Dispatchers.Default: As we can probably tell, this is the default Dispatcher.
                            This launches our Coroutine by using a common pool of threads.
                            Normally, the number of threads used here equal the number of cores present on the device the app is running on.


        Dispatchers.Main: This is the Dispatcher which confines itself to the Main thread,
                          and hence, only using one thread.


        Dispatchers.IO: This provides a shared pool of 64 on-demand created threads to our Coroutine.
                    This Dispatcher is used to execute long-running and blocking I/O operations.


        Dispatchers.Unconfined: This lets our Coroutine run on the current thread,
                                but if it is suspended and resumed, it will run on whichever thread that the suspending function is running on.
                                Please note that an unconfined Dispatcher shouldn’t normally be used in code.



        Apart from using these 4 Dispatchers, we can also:

                   convert Executors into Dispatchers by calling the asCoroutineDispatcher() extension function on them;
                   and create private thread pools for your Coroutines
                   with newSingleThreadContext and newFixedThreadPoolContext.
    ```


### Launch vs Async in Kotlin Coroutines

```
    The difference is that the launch{} does return an instance of Job and the async{} dose return an instance of Deferred<T>,

    which has an await()function that returns the result of the coroutine like we have future in Java.

    and we do future.get() in Java to the get the result.

    Suspend functions are only allowed to be called from a coroutine or another suspend function.


    we can see that in Kotlin, launch{}, async{}, and await() are regular functions

    marked with the suspend keyword.

    This gives a more flexible tool into the hands of the developers in terms of naming and return types.
```







### What Is Continuation
```
    When talking about coroutines we must talk about Continuation,

    it is Continuation that plays a very important role.

    Imagine we make a call to a function

    but when we returned from the function what if we can return not just the result of the function but a context of where we returned
```













































































