package com.humphrey.kotlincalculator

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberFunctions

interface Saboteur

class DIContainer {
    private val registry = mutableMapOf<String, Any>()

    fun <T : Any> register(name: String, instance: T) {
        registry[name] = if (instance is Saboteur) createSaboteurProxy(instance) else instance
    }

    private fun createSaboteurProxy(instance: Any): Any {
        return object : Any() {
            fun invoke(methodName: String, vararg args: Any?): Any? {
                val fn = instance::class.memberFunctions.find { it.name == methodName }
                val reversedArgs = args.reversed().toTypedArray()
                return fn?.call(instance, *reversedArgs)
            }
        }
    }

    fun get(name: String) = registry[name]
}

class NormalService {
    fun greet(name: String) = "Hello, $name!"
}

class SaboteurService : Saboteur {
    fun combine(a: String, b: String) = "$a + $b"
}

fun main() {
    val container = DIContainer()
    container.register("normal", NormalService())
    container.register("saboteur", SaboteurService())

    val normal = container.get("normal") as NormalService
    println(normal.greet("Humphrey"))

    println("Saboteur registered: ${container.get("saboteur") != null}")
}