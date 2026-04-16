package com.humphrey.kotlincalculator

data class Alien(var name: String?, var age: Int?)

fun processAliens(aliens: List<Alien>): List<Alien> {
    for (alien in aliens) {
        if (alien.name == null && alien.age != null && alien.age!! % 2 == 0) {
            alien.name = "Zog-${alien.age}"
        } else if (alien.age == null && alien.name != null && alien.name!!.length == 4) {
            alien.age = alien.name!![0].code
        }
    }
    return aliens
}

fun main() {
    val aliens = listOf(
        Alien(null, 10),
        Alien("Zorg", null),
        Alien(null, 7),
        Alien("Al", null)
    )

    val processed = processAliens(aliens)
    for (alien in processed) {
        println("Name: ${alien.name}, Age: ${alien.age}")
    }
}