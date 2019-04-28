#       Kotlin Coroutine 与 Spring Reactor的整合

```
    Kotlin Coroutine与Spring Reactor的整合主要是通过kotlinx-coroutines-reactive和kotlinx-coroutines-reactor实现的.
```

### 参考示例代码

```
        @GetMapping("/coroutine/{personId}")
        fun getMessages(@PathVariable personId: String): Mono<String> = GlobalScope.mono(Unconfined){

            val person = peopleRepository.findById(personId).awaitFirstOrDefault(null)?: throw NoSuchElementException("No person can be found by $personId")

            val lastLogin = auditRepository.findByEmail(person.email).awaitSingle().eventDate

            val numberOfMessages = messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin, person.email).awaitSingle()

            val message = "Hello ${person.name}, you have $numberOfMessages messages since $lastLogin"

            message
        }


        mono(Unconfined),awaitFirstOrDefault(null),awaitSingle()

        上面三个函数将是我们介绍的重点,可以分为两类

                1:mono(Unconfined){ }函数的实现原理

                2:awaitXXX{}函数的实现原理

        深刻理解以上两点,将为我们整合kotlin couroutine和spring webflux框架打开方便之门.

```

### Reactive Streams和Spring Reactor核心知识点介绍

*   Publisher ---> Mono/Flux

*   Mono.create(Consumer<MonoSink<T>>)

```
    Reactive Streams规范是Pivotal,Netflix等公司提出的一个反应式编程的一个规范.

    Reactive Streams规范定义了四个非常重要的接口,分别为Publisher,SubScribe,Process,Subscription

    Publisher 是其中一个重要的接口

    public interface Publisher<T> {
        public void subscribe(Subscriber<? super T> s);
    }

    Publisher顾名思义,发布者,等同于RxJava技术中的Observable.

    subscribe函数应该理解为a Publisher 被另外一个 a Subscriber订阅。

    不同于MQ消息队列,MQ消息队列中订阅和消息的发布是两个独立的环节,

    Publisher.subscribe(Subscriber)函数如果被调用,则这个Publisher开始发布消息.

    Spring Reactor实现了Reactive Streams规范.

    其中Mono和Flux是Spring Reactor中的两个重要的类,这两个类实现了Publisher接口.

    不同于类似的RxJava2,其只有一个Publisher接口的实现类.

    Spring Reactor中有两个Publisher接口的实现类,分别代表单个元素Mono和多个元素Flux两种场景。

    在使用了Spring WebFlux(基于Spring Reactor的新一代的Web框架)的项目中,

    一个被@RequestMapping标注的方法需要返回Mono或Flux.

    然后,当一个请求根据映射配置被转发到这个方法上时,一个Mono或Flux对象会根据这个方法的定义创建出一个 Mono.

    但这时真正的请求处理并未开始,方法返回的只是一个处理的步骤定义.

    当Spring WebFlux框架得到这个方法返回的Mono或Flux之后,会调用它们的subscribe(Subcriber)方法.此时,真正的请求处理便开始了.
```

```
    Spring WebFlux和传统Spring MVC最大的不同就是要求方法返回Mono或Flux.

    当Spring WebFlux与Kotlin Coroutine整合后,我们需要通过Coroutine Builder创建出一个协程Coroutine,

    再通过协程Coroutine来创建对应的Mono(或者Flux),有关的说明如下:

    Kotlin Coroutine使用的是Mono.create(Consumer<MonoSink<T>>)函数来创建Mono<T>

    Mono.create函数涉及到最主要的接口是MonoSink.

    MonoSink: Wrapper API around an actual downstream Subscriber for emitting nothing, a single value or an error (mutually exclusive)

   简单理解就是对后续Subscriber的封装.

   因为MonoSink是对后续Subscriber的封装,所以可以利用MonoSink向后续的Subscriber输出一些东西的.

   在 Kotlin Coroutine与Spring Reactor整合的过程中,Kotlin Coroutine将创建一个Coroutine,

   并将执行结果通过MonoSink输出给Subscriber。

   在Spring WebFlux的应用开发中,Subscriber会将Mono<T>(或Flux<T>)数据输出给HTTP EndPoints[HTTP 请求端].

```

```
   Kotlin Coroutine与Spring Reactor整合的关键点是通过扩展函数来实现的,即为CoroutineScope接口

   提供mono{}函数和flux{}函数以及一些辅助的函数例如awaitXXX{}系列函数,通过这些扩展函数我们就可以返回Mono<T>

   和Flux<T>数据了，下面就来看看一些重要的扩展函数以便我们更好的理解框架之间的整合.
```

### CoroutineScope.mono{}扩展函数

```
    /**
     * Creates cold [mono][Mono] that will run a given [block] in a coroutine.
     * Every time the returned mono is subscribed, it starts a new coroutine.
     * Coroutine returns a single, possibly null value. Unsubscribing cancels running coroutine.
     *
     * | **Coroutine action**                  | **Signal to sink**
     * | ------------------------------------- | ------------------------
     * | Returns a non-null value              | `success(value)`
     * | Returns a null                        | `success`
     * | Failure with exception or unsubscribe | `error`
     *
     * Coroutine context is inherited from a [CoroutineScope], additional context elements can be specified with [context] argument.
     * If the context does not have any dispatcher nor any other [ContinuationInterceptor], then [Dispatchers.Default] is used.
     * The parent job is inherited from a [CoroutineScope] as well, but it can also be overridden
     * with corresponding [coroutineContext] element.
     *
     * @param context context of the coroutine.
     * @param block the coroutine code.
     */
    fun <T> CoroutineScope.mono(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T?
    ): Mono<T> = Mono.create { sink ->
        // 创建一个新的CoroutineContext
        val newContext = newCoroutineContext(context)

        // 创建一个新的MonoCoroutine
        // MonoCoroutine实现了Disposable接口
        val coroutine = MonoCoroutine(newContext, sink)

        sink.onDispose(coroutine)

        coroutine.start(CoroutineStart.DEFAULT, coroutine, block)
    }


    mono{}函数最主要的部分都集中在对Mono.create函数的调用.

    Mono.create函数会创建出一个Mono对象,

    当这个Mono对象的subscribe函数被执行的时候,

    传入到Mono.create函数的Consumer就会被调用.此时下面的代码就会被执行

        { sink ->
            val newContext = newCoroutineContext(context)
            val coroutine = MonoCoroutine(newContext, sink)
            sink.onDispose(coroutine)
            coroutine.start(CoroutineStart.DEFAULT, coroutine, block)
        }

    上述代码块创建了一个 Coroutine对象[MonoCoroutine],并且sink对象被传入到MonoCoroutine中,接着执行该协程.

    private class MonoCoroutine<in T>(
        parentContext: CoroutineContext,
        private val sink: MonoSink<T>
    ) : AbstractCoroutine<T>(parentContext, true),Disposable {
        var disposed = false

        override fun onCompleted(value: T) {
            if (!disposed) {
                if (value == null) sink.success() else sink.success(value)
            }
        }

        override fun onCancelled(cause: Throwable, handled: Boolean) {
            if (!disposed) {
                sink.error(cause)
            } else if (!handled) {
                handleCoroutineException(context, cause)
            }
        }

        override fun dispose() {
            disposed = true
            cancel()
        }

        override fun isDisposed(): Boolean = disposed
    }

    MonoCoroutine继承了AbstractCoroutine抽象类,并且实现了Disposable接口,同时构造函数入参传入了MonoSink.

    MonoCoroutine实现了两个在AbstractCoroutine声明的重要函数：

    onCompleted{}和onCompletedExceptionally{}这两个函数都是回调函数.

    当MonoCoroutine在执行完毕之后,即这两个回调函数被调用时,会通过调用MonoSink将结果输出给Subscriber.

```

### Publisher<T>.awaitXXX{}扩展函数

```
    /**
     * Awaits for the first value from the given publisher without blocking a thread and
     * returns the resulting value or throws the corresponding exception if this publisher had produced error.
     *
     * This suspending function is cancellable.
     * If the [Job] of the current coroutine is cancelled or completed while this suspending function is waiting, this function
     * immediately resumes with [CancellationException].
     *
     * @throws NoSuchElementException if publisher does not emit any value
     */
    public suspend fun <T> Publisher<T>.awaitFirst(): T = awaitOne(Mode.FIRST)

    /**
     * Awaits for the first value from the given observable or the [default] value if none is emitted without blocking a
     * thread and returns the resulting value or throws the corresponding exception if this observable had produced error.
     *
     * This suspending function is cancellable.
     * If the [Job] of the current coroutine is cancelled or completed while this suspending function is waiting, this function
     * immediately resumes with [CancellationException].
     */
    public suspend fun <T> Publisher<T>.awaitFirstOrDefault(default: T): T = awaitOne(Mode.FIRST_OR_DEFAULT, default)

    /**
     * Awaits for the first value from the given observable or `null` value if none is emitted without blocking a
     * thread and returns the resulting value or throws the corresponding exception if this observable had produced error.
     *
     * This suspending function is cancellable.
     * If the [Job] of the current coroutine is cancelled or completed while this suspending function is waiting, this function
     * immediately resumes with [CancellationException].
     */
    public suspend fun <T> Publisher<T>.awaitFirstOrNull(): T? = awaitOne(Mode.FIRST_OR_DEFAULT)

    /**
     * Awaits for the first value from the given observable or call [defaultValue] to get a value if none is emitted without blocking a
     * thread and returns the resulting value or throws the corresponding exception if this observable had produced error.
     *
     * This suspending function is cancellable.
     * If the [Job] of the current coroutine is cancelled or completed while this suspending function is waiting, this function
     * immediately resumes with [CancellationException].
     */
    public suspend fun <T> Publisher<T>.awaitFirstOrElse(defaultValue: () -> T): T = awaitOne(Mode.FIRST_OR_DEFAULT) ?: defaultValue()

    /**
     * Awaits for the last value from the given publisher without blocking a thread and
     * returns the resulting value or throws the corresponding exception if this publisher had produced error.
     *
     * This suspending function is cancellable.
     * If the [Job] of the current coroutine is cancelled or completed while this suspending function is waiting, this function
     * immediately resumes with [CancellationException].
     *
     * @throws NoSuchElementException if publisher does not emit any value
     */
    public suspend fun <T> Publisher<T>.awaitLast(): T = awaitOne(Mode.LAST)

    /**
     * Awaits for the single value from the given publisher without blocking a thread and
     * returns the resulting value or throws the corresponding exception if this publisher had produced error.
     *
     * This suspending function is cancellable.
     * If the [Job] of the current coroutine is cancelled or completed while this suspending function is waiting, this function
     * immediately resumes with [CancellationException].
     *
     * @throws NoSuchElementException if publisher does not emit any value
     * @throws IllegalArgumentException if publisher emits more than one value
     */
    public suspend fun <T> Publisher<T>.awaitSingle(): T = awaitOne(Mode.SINGLE)

    // ------------------------ private ------------------------

    private enum class Mode(val s: String) {
        FIRST("awaitFirst"),
        FIRST_OR_DEFAULT("awaitFirstOrDefault"),
        LAST("awaitLast"),
        SINGLE("awaitSingle");
        override fun toString(): String = s
    }

    private suspend fun <T> Publisher<T>.awaitOne(
        mode: Mode,
        default: T? = null
    ): T = suspendCancellableCoroutine { cont ->
        subscribe(object : Subscriber<T> {
            private lateinit var subscription: Subscription
            private var value: T? = null
            private var seenValue = false

            override fun onSubscribe(sub: Subscription) {
                subscription = sub
                cont.invokeOnCancellation { sub.cancel() }
                sub.request(if (mode == Mode.FIRST) 1 else Long.MAX_VALUE)
            }

            override fun onNext(t: T) {
                when (mode) {
                    Mode.FIRST, Mode.FIRST_OR_DEFAULT -> {
                        if (!seenValue) {
                            seenValue = true
                            subscription.cancel()
                            cont.resume(t)
                        }
                    }
                    Mode.LAST, Mode.SINGLE -> {
                        if (mode == Mode.SINGLE && seenValue) {
                            subscription.cancel()
                            if (cont.isActive)
                                cont.resumeWithException(IllegalArgumentException("More than one onNext value for $mode"))
                        } else {
                            value = t
                            seenValue = true
                        }
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun onComplete() {
                if (seenValue) {
                    if (cont.isActive) cont.resume(value as T)
                    return
                }
                when {
                    mode == Mode.FIRST_OR_DEFAULT -> {
                        cont.resume(default as T)
                    }
                    cont.isActive -> {
                        cont.resumeWithException(NoSuchElementException("No value received via onNext for $mode"))
                    }
                }
            }

            override fun onError(e: Throwable) {
                cont.resumeWithException(e)
            }
        })
    }


```















































































