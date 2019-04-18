#   Introduction to Coroutines【协程导论】

### What Is The Aim Of Coroutines
```
    The purpose of coroutines is to take care of the complications in working with asynchronous programming.

    We write code sequentially, like we usually do, and then leave to the coroutines the hard work.

    Coroutines are a low-level mechanism. The end objective is to build  accessible mechanisms like async/await in C#;

    Coroutines have been created to manage operations with long execution time

    (e.g., Input/Output operations with a remote resource) without blocking a thread.

    They allows to suspend a computation without keeping occupied a thread.

    In fact, traditional threads have a flaw: they are costly to maintain.

    Thus it is not practical to have more than a few available and they are mostly controlled by the system.

    A coroutine offers a lightweight alternative that is cheap in terms of resources

    and it is easier to control by the developer.
```

### What Problems Do Coroutines Help Us To Solve?
```
    PROBLEM【传统Java应用中的问题】	                                    SOLUTION【Kotlin中协程的对应解决方案】

    Simplify Callbacks【传统应用中的Callback hell】	                        Coroutines

    Get results from a potentially infinite list	                        BuildSequence

    Get a promise for a future result	                                    Async/Await

    Work with streams of data	                                            Channels and Pipelines

    Act on multiple asynchronous inputs	                                    Select
```

### Suspension Points【协程挂起点】
```
    coroutines are blocks of code that can be suspended, without blocking a thread.

    The difference between blocking and suspending is that blocking a thread

    means the thread cannot do anything else,

    while suspending it means that it can do other things

    while waiting the completion of the suspended block.

    However we cannot suspend a coroutine at arbitrary positions.

    A coroutine can be suspended only at certain points, called suspension points.

    That is to say functions with the modifier suspend. for example:

    suspend fun answer() {
          println("Hello to you!")
    }


```

### How To Invoke Suspending Functions

```
    Functions with suspension points works normally except for one thing:

    they can only be called from coroutines or other functions with suspension points.

    They cannot be called by normal code. The calling function can be a (suspending) lambda
```

### Launching A Suspension Function

```
    The simplest way to launch a suspension function is with the launch function.


    suspend fun answer() {
          println("Hello to you!")
    }

    fun main() = runBlocking {
        launch {
            answer() // it prints this second
        }

        println("Hello, dude!") // it prints this first
        Thread.sleep(2000L) // it simulates real work being done
    }

```


### From Callbacks to Coroutines
```
Coroutines are useful to get rid of callbacks.

Assume that we have a function that must perform some expensive computation: solveAllProblems.

This function calls a callback that receives the result of the computation and performs what is needed (e.g., it saves the result in a database).

回调接口函数
fun solveAllProblems(params: Params, callback: (Result) -> Unit)

we can easily eliminate the callback using the suspendCoroutine function.

This function works by relying on a Continuation type (cont in the following example),

that offers a resume method, which is used to return the expected result.

suspend fun solveAllProblems(params: Params): Result = suspendCoroutine { cont ->
    solveAllProblems(params) { cont.resume(it) }
}


The advantage of this solution is that now the return type of this computation is explicit.,

but the computation itself is still asynchronous and does not block a thread.
```