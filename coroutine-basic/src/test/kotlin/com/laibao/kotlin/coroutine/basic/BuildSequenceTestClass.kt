package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.runBlocking
import org.junit.Test

class BuildSequenceTestClass {

    @Test
    fun testBuildSequence() = runBlocking{


        /**
         * The Sequence is a wrapper API provided in Kotlin.
         * It’s going to build a sequence lazily yielding one by one.
         */
        // 1
        val sequence1 = sequence {
            println("One")
            yield(1)

            println("Two")
            yield(2)

            println("Three")
            yield(3)
        }

        // 2
        sequence1.forEach { println(it) }

    }
}