package com.humphrey.kotlincalculator

val primeDigits = setOf(2, 3, 5, 7)

interface TemporalAnomaly {
    fun getTime(): Long
}

class VolatileClock : TemporalAnomaly {
    private var lastTime: Long = System.currentTimeMillis()
    private var offset: Long = 0

    override fun getTime(): Long {
        val realTime = System.currentTimeMillis()
        val lastDigit = (realTime % 10).toInt()

        return if (lastDigit in primeDigits) {
            offset = 0
            lastTime = realTime
            realTime
        } else {
            offset += 3000
            val anomalyTime = realTime - offset
            lastTime = anomalyTime
            anomalyTime
        }
    }
}

fun main() {
    val clock = VolatileClock()
    repeat(5) {
        val time = clock.getTime()
        println("Time: $time (last digit: ${time % 10})")
        Thread.sleep(100)
    }
}