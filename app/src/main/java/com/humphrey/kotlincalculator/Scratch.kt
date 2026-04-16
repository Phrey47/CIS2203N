package com.humphrey.kotlincalculator

fun chronoDecoder(input: String): Map<Char, Int> {
    val variables = mutableMapOf<Char, Int>()
    val operations = mutableListOf<Triple<Char, Char, Int>>()

    for (part in input.split(", ")) {
        val variable = part[0]
        val operator = part[1]
        val value = part.substring(2).toInt()
        variables[variable] = 1
        operations.add(Triple(variable, operator, value))
    }

    for ((variable, operator, value) in operations.sortedBy { it.first }) {
        val current = variables[variable] ?: 1
        variables[variable] = when (operator) {
            '+' -> current + value
            '-' -> current - value
            '*' -> current * value
            '/' -> if (value != 0) current / value else current
            else -> current
        }
    }

    return variables.toSortedMap()
}

fun main() {
    println(chronoDecoder("A+3, C-2, B*2"))
    println(chronoDecoder("B+5, A*3, C-1"))
}