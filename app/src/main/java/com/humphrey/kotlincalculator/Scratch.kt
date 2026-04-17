package com.humphrey.kotlincalculator

fun fibSequence(): Sequence<Int> = sequence {
    var a = 0
    var b = 1
    while (true) {
        yield(a % 256)
        val next = (a + b) % 256
        a = b
        b = next
    }
}

@JvmInline
value class EncryptedString(val bytes: ByteArray) {
    operator fun plus(other: EncryptedString): EncryptedString {
        val maxLen = maxOf(bytes.size, other.bytes.size)
        val fib = fibSequence().take(maxLen).toList()

        val paddedA = if (bytes.size < maxLen)
            bytes + fib.drop(bytes.size).map { it.toByte() }.toByteArray()
        else bytes

        val paddedB = if (other.bytes.size < maxLen)
            other.bytes + fib.drop(other.bytes.size).map { it.toByte() }.toByteArray()
        else other.bytes

        return EncryptedString(ByteArray(maxLen) { i -> (paddedA[i].toInt() xor paddedB[i].toInt()).toByte() })
    }

    fun toReadableString() = bytes.joinToString(", ") { it.toInt().and(0xFF).toString() }
}

fun main() {
    val a = EncryptedString("Hello".toByteArray())
    val b = EncryptedString("Hi".toByteArray())
    val result = a + b
    println("Encrypted result: ${result.toReadableString()}")
}