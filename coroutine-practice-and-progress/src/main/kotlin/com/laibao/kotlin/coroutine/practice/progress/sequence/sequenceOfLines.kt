package com.laibao.kotlin.coroutine.practice.progress.sequence

import java.io.BufferedReader
import java.io.FileReader

fun main() {
    val fileName = "D:\\IdeaProjects\\git\\kotlin-coroutine-learning\\coroutine-practice-and-progress\\src\\main\\kotlin\\com\\laibao\\kotlin\\coroutine\\practice\\progress\\sequence\\sequence.kt"

    sequenceOfLines(fileName).forEach(::println)
}

fun sequenceOfLines(fileName: String) = sequence<String> {

    BufferedReader(FileReader(fileName)).use {
                    while (true) {
                        yield(it.readLine() ?: break)
                    }
    }
}