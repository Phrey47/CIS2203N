package com.humphrey.kotlincalculator

fun digitSum(n: Int): Int {
    return n.toString().map { it.digitToInt() }.sum()
}

fun alternatingSum(numbers: List<Int>): Int {
    var result = 0
    for (i in numbers.indices) {
        val value = if (numbers[i] % 7 == 0) digitSum(numbers[i]) else numbers[i]
        result += if (i % 2 == 0) value else -value
    }
    return result
}

fun main() {
    println(alternatingSum(listOf(1, 2, 3, 4, 5)))
    println(alternatingSum(listOf(7, 2, 14, 4, 21)))
    println(alternatingSum(listOf(10, 7, 3, 14, 5)))
}