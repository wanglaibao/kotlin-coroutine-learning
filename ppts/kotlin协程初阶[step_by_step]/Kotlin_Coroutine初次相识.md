#                   Kotlin Coroutine初次相识

### Kotlin Coroutine 是什么

```
    Kotlin Coroutine是Kotlin为了实现更好的异步和并发程序所提供的一个特性.

    不同于其它的编程语言,Kotlin将其Coroutin特性的大部分内容作为了一个扩展库:kolinx.coroutines,

    语言层面仅提供了有限的支持.

    C#,GoLang等语言都将async,promise,await,yield做为了关键字,但在Kotlin中,这些只是普通的函数.

    在Kotlin中,和Coroutine相关的关键字仅有suspend.

    不仅如此,Kotlin还将Coroutin库做了进一步拆分,分成了核心模块kotlinx-coroutines-core和与其它异步,并发技术集成的模块.

    例如:kotlinx-coroutines-jdk8,kotlinx-coroutines-reactive,kotlinx-coroutines-reactor,kotlinx-coroutines-rx1/rx2等.

```

### Kotlin Coroutine 的使用场景

```
     下面我们通过举例子来说明Kotlin Coroutine在不同场景中如何解决我们在异步和并发编程中所遇到的问题和痛点.

     1:延迟执行

     我们在开发的时候,经常遇到需要等待一段时间之后在执行某些任务的场景.这时,我们常用Thread.sleep休眠一些时间来实现

        @Test
        fun delay_with_thread() {
            log.info("Hello")
            Thread.sleep(1000)
            log.info("World")
        }

     这样做效率比较低,因为阻塞了任务的执行白白地浪费了一秒钟.如果有大量类似代码逻辑的话,那就很浪费资源了.

     我们可以通过使用ScheduledThreadPool来改进.

         @Test
         fun delay_with_scheduler() {
             val scheduler = Executors.newScheduledThreadPool(1)
             log.info("Hello")
             scheduler.schedule({
                 log.info("World")
             }, 1, TimeUnit.SECONDS)
             scheduler.shutdown()
             scheduler.awaitTermination(1, TimeUnit.SECONDS)
         }

     采用Kotlin Coroutine可以进一步来改进优化
        @Test
        fun delay_with_coroutine() {
            runBlocking {
                log.info("Hello")
                delay(1000)
                log.info("World")
            }
        }

     是不是很简单呢?和第一个版本唯一的区别就是把Thread.sleep(1000)换成了delay(1000).
     并且delay(1000)并不会阻塞当前线程的执行,这样代码执行效率就高的多了.

     2:Completable Future
        Kotlin Coroutine提供了与各种异步技术的集成,包括JDK8 CompletableFuture,Google Guava的ListenableFuture,

        Spring的Reactor,Netflix的RxJava2等,

        但不包括JDK5中的Future.

        原因是传统的Future接口并没有提供任何回掉机制,所以Kotlin Coroutine无法与其集成.

        接下来介绍一下Kotlin Coroutine如何与CompletableFuture集成的.

        按照传统方式使用 CompletableFuture需要调用thenApply,thenCompose,thenAccept这样的方法串联起异步调用：

        val future = CompletableFuture.supplyAsync({ 1 })

        future.thenApply { value -> "${value + 2}" }
              .thenAccept({ value -> log.info(value.toString())})

        Kotlin Coroutine为CompletableFuture接口增加了await扩展函数,可以将回调转换为传统的调用方式

        val future = CompletableFuture.supplyAsync({ 1 })

        val value = future.await()

        val result = value + 2

        log.info(result.toString())

        可见使用Kotlin Coroutine之后代码得到了明显简化

     3: Rx反应式编程

     接下来我们来看看Kotlin Coroutine是如何简化反应式编程的

        A:直接使用Spring Reactor

            下面这段代码的目的是根据人员的personId查询在他上次登录之后,又有多少新消息.其中使用到了Spring5的反应式编程特性.

            使用了Reactor的API和Spring Data中的ReactiveRepository.

            @GetMapping("/reactive/{personId}")
            fun getMessages(@PathVariable personId: String): Mono<String>{
              return peopleRepository.findById(personId)
                                    .switchIfEmpty(Mono.error(NoSuchElementException()))
                                    .flatMap { person -> auditRepository.findByEmail(person.email)
                                                                        .flatMap{ lastLogin -> messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin.eventDate, person.email)
                                                                                                                .map { numberOfMessages -> "Hello ${person.name}, you have $numberOfMessages messages since ${lastLogin.eventDate}"}
                                                                                }
                                             }
            }


        上面的代码还是逃脱不了 Callback Hell的窠臼.

        Reactive Stream方式不是说可以避免Callback Hell吗?为什么这里还是存在Callback Hell.

        其实,像Reactor,RxJava2这样的Reactive框架,所能解决的Callback Hell问题的范围是有限的.

        一般来说,如果一系列的调用,每一步只依赖上一步的结果,那用Reactive Stream的方式可以完美的写成链式调用.

        eg:
            monoA.flatMap(valueA -> {
                                        return  MonoB(valueA);
                                    }
                         )
                 .flatMap(valueB -> {
                                        return  MonoC(valueB);
                                    }
                          )
                 .flatMap(valueC -> {
                                        return  MonoD(valueC);
                                    }
                         );

        上面代码中逻辑中,monoA中包含的值是valueA,通过valueA查询得到的MonoB中包含的值是valueB依次类推.

        但问题是,现实中的业务需求哪里会这么简单和理想.

        以上面的查询新消息数的应用为例,messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin.eventDate, person.email)

        这一步依赖了上一步的结果lastLogin和上上步的结果person.

        不满足我之前所说的"每一步只依赖上一步的结果"的条件,导致这个例子不太容易写成完美链式调用.


     B:使用Kotlin Coroutine来简化

        @GetMapping("/coroutine/{personId}")
        fun getMessages(@PathVariable personId: String): Mono<String> = GlobalScope.mono(Unconfined){

            val person = peopleRepository.findById(personId).awaitFirstOrDefault(null)?: throw NoSuchElementException("No person can be found by $personId")

            val lastLogin = auditRepository.findByEmail(person.email).awaitSingle().eventDate

            val numberOfMessages = messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin, person.email).awaitSingle()

            val message = "Hello ${person.name}, you have $numberOfMessages messages since $lastLogin"

            message
        }

        从上面的代码逻辑中,我们可以看到经过Kotlin Coroutine改造之后代码最明显的变化就是代码可读性提高了很多.

        代码的可读性对所有的软件系统都是十分重要,如果代码很难让人理解,那软件系统的维护,升级工作的成本就会很高.

        因此Kotlin Coroutine对异步编程的代码可读性的提升是非常有价值的.

        另外需要指出的是,如果查询结果为空的话,调用awaitSingle{}函数会导致程序抛出NoSuchElementException,

        并无法直接通过try{}catch{}来进行捕获(只能通过Mono的错误处理有关的回调函数来进行处理,如doOnError{},onErrorCosume{}函数等).

        为了兼容查询结果可能为空的情况,使用了awaitFirstOrDefault{}函数.

```


### 总结与展望

```
    如今面对高并发应用开发场景,Java传统的线程模型的并发处理方案显得越来越力不从心.

    Java社区也意识到了这个问题,于是出现了一批提供轻量级线程解决方案的项目,如Quasar项目,OpenJDK的Project Loom提案,

    也包括反应式编程技术,但这些方案都存在这样或那样的问题.

    Kotlin Coroutine的出现为解决JVM平台的高并发应用开发提供了新的选择,带来了新的希望.

    但我们也需要看到,Kotlin Coroutine虽然在形式上简化了异步代码的开发,但也对开发人员的使用者提出了相当的要求.

    如果对Java并发,NIO,反应式编程,以及Kotlin技术栈本身缺乏足够的了解和掌握的情况下,

    那恐怕还是难以顺畅使用Kotlin Coroutine的.
```






















































































