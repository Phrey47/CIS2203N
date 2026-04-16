package com.humphrey.kotlincalculator

fun bouncingSequence(start: Int): Sequence<Long> = sequence {
    var current = start.toLong()
    val history = mutableListOf<Long>()
    var count = 0

    while (true) {
        count++
        if (count % 5 == 0) {
            current = history.takeLast(4).sum()
        } else {
            current = if (current % 2 == 0L) current / 2 else current * 3 + 1
        }

        if (current % 13 == 0L) {
            yield(current)
            break
        }

        history.add(current)
        yield(current)
    }
}

fun main() {
    println(bouncingSequence(10).toList())
    println(bouncingSequence(27).toList())
}