package com.humphrey.kotlincalculator

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.juggle(capacity: Int): Flow<T> = flow {
    val buffer = mutableListOf<T>()

    collect { value ->
        buffer.add(value)
        if (buffer.size >= capacity) {
            buffer.removeAll { it is Int && (it as Int) % 2 != 0 }
        }
        if (buffer.size >= capacity) {
            while (buffer.size >= capacity) {
                delay(10)
            }
        }
        if (buffer.isNotEmpty()) {
            emit(buffer.removeAt(0))
        }
    }

    while (buffer.isNotEmpty()) {
        emit(buffer.removeAt(0))
    }
}

fun main() = runBlocking {
    val numbers = (1..20).asFlow()

    numbers.juggle(5).collect { value ->
        println("Collected: $value")
        delay(20)
    }
}
