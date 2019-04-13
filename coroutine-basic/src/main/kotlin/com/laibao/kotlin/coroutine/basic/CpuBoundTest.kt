package com.laibao.kotlin.coroutine.basic

val words = listOf("level", "pope", "needle", "Anna", "Pete", "noon", "stats")

/**
 * CPU-bound algorithm.
 */
fun main() {
    filterPalindromes(words).forEach {
        println(it)
    }
}

fun filterPalindromes(words: List<String>) : List<String> {
    return words.filter { isPalindrome(it) }
}

fun isPalindrome(word: String) : Boolean {
    val lcWord = word.toLowerCase()
    return lcWord == lcWord.reversed()
}