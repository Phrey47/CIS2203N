package com.humphrey.kotlincalculator

class StateMachine<T : Any> {
    private var currentState: T? = null
    private val transitions = mutableListOf<Pair<T, T>>()

    fun countVowels(s: String): Int {
        return s.count { it.lowercaseChar() in "aeiou" }
    }

    fun initial(state: T) {
        currentState = state
    }

    fun transition(from: T, to: T) {
        transitions.add(Pair(from, to))
    }

    fun trigger(to: T): Boolean {
        val current = currentState ?: return false
        val fromVowels = countVowels(current::class.simpleName ?: "")
        val toVowels = countVowels(to::class.simpleName ?: "")

        return if (toVowels > fromVowels) {
            val valid = transitions.any { it.first == current && it.second == to }
            if (valid) {
                currentState = to
                println("Transitioned from ${current::class.simpleName} to ${to::class.simpleName}")
                true
            } else {
                println("No transition defined from ${current::class.simpleName} to ${to::class.simpleName}")
                false
            }
        } else {
            println("Transition blocked: ${to::class.simpleName} has fewer or equal vowels than ${current::class.simpleName}")
            false
        }
    }

    fun currentState() = currentState
}

sealed class AppState
object Idle : AppState()
object Processing : AppState()
object Authenticated : AppState()
object Error : AppState()

fun main() {
    val machine = StateMachine<AppState>()
    machine.initial(Idle)
    machine.transition(Idle, Processing)
    machine.transition(Processing, Authenticated)
    machine.transition(Authenticated, Error)

    machine.trigger(Processing)
    machine.trigger(Authenticated)
    machine.trigger(Error)

    println("Current state: ${machine.currentState()!!::class.simpleName}")
}