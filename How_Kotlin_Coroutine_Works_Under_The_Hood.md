# How Kotlin Coroutine Works Under The Hood
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


### Launch vs Async in Kotlin Coroutines

```
    The difference is that the launch{} does return an instance of Job and the async{} dose return an instance of Deferred<T>,

    which has an await()function that returns the result of the coroutine like we have future in Java.

    and we do future.get() in Java to the get the result.


    Suspend functions are only allowed to be called from a coroutine or another suspend function.
```







### What Is Continuation
```
    When talking about coroutines we must talk about Continuation,

    it is Continuation that plays a very important role.

    Imagine we make a call to a function

    but when we returned from the function what if we can return not just the result of the function but a context of where we returned
```













































































