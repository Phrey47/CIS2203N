package com.humphrey.kotlincalculator

fun greetUser(name: String): String {
    return "Hello, $name!"
}

fun main() {
    val greeting = greetUser("Humphrey")
    println(greeting)
}