package com.humphrey.kotlincalculator

import kotlinx.coroutines.*

class TimeParadoxException(message: String) : Exception(message)

fun detectCycle(graph: Map<String, List<String>>): Boolean {
    val visited = mutableSetOf<String>()
    val inStack = mutableSetOf<String>()

    fun dfs(node: String): Boolean {
        visited.add(node)
        inStack.add(node)
        for (neighbor in graph[node] ?: emptyList()) {
            if (neighbor !in visited && dfs(neighbor)) return true
            if (neighbor in inStack) return true
        }
        inStack.remove(node)
        return false
    }

    for (node in graph.keys) {
        if (node !in visited && dfs(node)) return true
    }
    return false
}

suspend fun executejobs(graph: Map<String, List<String>>): Map<String, String> {
    if (detectCycle(graph)) {
        throw TimeParadoxException("Circular deadlock detected in job graph!")
    }

    return coroutineScope {
        graph.keys.map { job ->
            async {
                delay(100)
                job to "completed"
            }
        }.awaitAll().toMap()
    }
}

fun main() = runBlocking {
    val safeGraph = mapOf(
        "JobA" to listOf("JobB"),
        "JobB" to listOf("JobC"),
        "JobC" to emptyList()
    )

    val cyclicGraph = mapOf(
        "JobA" to listOf("JobB"),
        "JobB" to listOf("JobC"),
        "JobC" to listOf("JobA")
    )

    try {
        val results = executejobs(safeGraph)
        println("Safe graph results: $results")
    } catch (e: TimeParadoxException) {
        println("Error: ${e.message}")
    }

    try {
        val results = executejobs(cyclicGraph)
        println("Cyclic graph results: $results")
    } catch (e: TimeParadoxException) {
        println("Error: ${e.message}")
    }
}