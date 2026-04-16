package com.humphrey.kotlincalculator

data class User(val username: String, var score: Int)

fun main() {
    val users = listOf(
        User("Humphrey", 100),
        User("Alice", 85),
        User("Bob", 90)
    )

    for (user in users) {
        println("${user.username} has a score of ${user.score}")
    }
}