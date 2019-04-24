# Kotlin_Coroutines_Cheat_Sheet

## CoroutineScope

```
    To start coroutine scope we can:
        1: use GlobalScope that has empty coroutine context.

        2: implement CoroutineScope interface.

        3: create a scope from a context with(CoroutineScope(context = context)) { ... }
```

## Coroutine context

```
    It is an indexed set of Element instances where every element in this set has a unique Key.

    EmptyCoroutineContext:: --> which does not change coroutine behavior at all, like an empty map.

    CoroutineName:: --> which Sets a name of a coroutine for debugging purposes

    Job::--> lifecycle of a coroutine, which can be used to cancel coroutine.
        A coroutine is responsible for all children with the same Job.
        It waits for them and cancels all of them if any had an error
        (To make children independent use SupervisorJob).

    CoroutineExceptionHandler::--> which used to set exception handling for uncaught exceptions.

    ContinuationInterceptor::--> Intercepts continuation, which mainly used by dispatchers.

```

## Coroutine dispachers

```
    Dispatchers.Default::--> Different thread (if possible), which is backed by a shared pool of threads on JVM.

    Dispatchers.Main::--> Platform specific main thread (if exists).

    Dispatchers.IO::--> Thread designed for offloading blocking IO tasks to a shared pool of threads.

    Dispatchers.Unconfined::--> Always uses first available thread (most performant dispatcher).

    newSingleThreadContext::--> Creates a new coroutine execution context using a single thread with built-in yield support.

    newFixedThreadPoolContext::--> Creates new coroutine execution context with the fixed-size thread-pool and built-in yield support
```




## Sequence builder

```
        val childNumbers = sequence {
                                        yield(1)
                                        println("AAA")
                                        yieldAll(listOf(2, 3))
                                    }

        // 1AAA23
        childNumbers.forEach { println(it) }

        // AAA
        val nums = childNumbers.joinToString()

        println(nums) // 1, 2, 3
```


## Channels

```
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {

                                                                  for (x in 1..5) send(x * x)

                                                                }
    val squares = produceSquares()
     // 1, 4, 9, 16, 25
    repeat(5) {
        println(squares.receive())
    }
    val squares2 = produceSquares()
    // 1, 4, 9, 16, 25
    for(square in squares2){
        println(square)
    }

```

## Deal with shared state

```
        AtomicInteger::--> There are atomics for primitives.

        AtomicReference<V>::--> Atomic reference.

        Mutex::--> Does not let more than one thread at the same time.

        eg:
           private val mutex = Mutex()

           mutex.withLock { /**/ }
```

## Actors

```

    sealed class Msg

    object IncCounter: Msg()

    object PrintCounter: Msg()

    class GetCounter(val resp: CompletableDeferred<Int>):Msg()

    fun CoroutineScope.counterActor() = actor<Msg> {

            var counter = 0 // Actor state
            for (msg in channel) {
                when (msg) {
                                is IncCounter -> counter++
                                is PrintCounter -> print(counter)
                                is GetCounter -> msg.resp.complete(counter)
                            }
            }
    }

```



