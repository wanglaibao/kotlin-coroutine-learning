package com.laibao.kotlin.coroutine.reactive

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SpringCoroutinesReactiveApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(SpringCoroutinesReactiveApplication::class.java, *args)
        }
    }
}