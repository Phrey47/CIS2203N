package com.humphrey.kotlincalculator

import kotlin.random.Random

class QuantumList<T : Comparable<T>>(private val elements: List<T>) {

    private var getCalls = 0

    fun get(index: Int): T {
        getCalls++
        val roll = Random.nextDouble()
        return when {
            roll < 0.80 -> elements[index]
            roll < 0.90 -> elements[minOf(index + 1, elements.size - 1)]
            else -> elements[Random.nextInt(elements.size)]
        }
    }

    fun findTrueMax(samples: Int = 5): T {
        val candidates = elements.indices.map { i ->
            (1..samples).map { get(i) }.groupBy { it }.maxByOrNull { it.value.size }!!.key
        }
        return candidates.max()
    }

    fun getCallCount() = getCalls
}

fun main() {
    val qList = QuantumList(listOf(3, 7, 1, 9, 4, 6, 2, 8, 5))
    val max = qList.findTrueMax()
    println("True maximum: $max")
    println("Total get() calls: ${qList.getCallCount()}")
}