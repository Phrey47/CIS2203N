package com.humphrey.kotlincalculator

fun String.vowelShift(): String {
    val vowels = "aeiouAEIOU"
    val consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ"
    var result = this

    if (this.length % 2 != 0) {
        for (i in 1 until this.length - 1) {
            val prev = this[i - 1]
            val curr = this[i]
            val next = this[i + 1]
            if (curr in vowels && prev in consonants && next in consonants && prev == next) {
                val block = "${prev}${curr.uppercaseChar()}${next}"
                result = result.replace("${prev}${curr}${next}", block + block)
            }
        }
    }
    return result
}

fun main() {
    println("banana".vowelShift())
    println("hello".vowelShift())
    println("abacus".vowelShift())
}