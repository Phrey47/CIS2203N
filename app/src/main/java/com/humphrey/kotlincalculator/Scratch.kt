package com.humphrey.kotlincalculator

fun isPalindrome(s: String): Boolean {
    return s == s.reversed()
}

fun countPalindromicSubstrings(s: String): Int {
    var count = 0
    for (i in s.indices) {
        for (j in i + 3..s.length) {
            if (isPalindrome(s.substring(i, j))) count++
        }
    }
    return count
}

fun palindromeWeight(s: String): Double {
    return if (s.isEmpty()) 0.0
    else countPalindromicSubstrings(s).toDouble() / s.length
}

fun sortByPalindromeWeight(words: List<String>): List<String> {
    return words.sortedWith(compareByDescending<String> { palindromeWeight(it) }
        .thenByDescending { it.count { c -> c == 'K' } })
}

fun main() {
    val words = listOf("racecar", "hello", "level", "Kotlin", "madam", "KooK")
    println(sortByPalindromeWeight(words))
}