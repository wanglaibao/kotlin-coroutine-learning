#       Kotlin Coroutine 与 Spring Reactor 的整合

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

                1:mono(Unconfined) { ... } 的实现原理

                2:awaitXXX 方法的实现原理

        理解了上面两点我们就可以能狗理解Kotlin Coroutine是如何与Spring Reactor整合的了,

        这将为我们后续在应用中使用kotlin couroutine和spring webflux框架的整合打开方便之门.
```












