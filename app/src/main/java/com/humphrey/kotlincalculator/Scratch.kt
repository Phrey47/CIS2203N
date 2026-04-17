package com.humphrey.kotlincalculator

sealed class JsonNode
data class Leaf(val key: String, val value: String) : JsonNode()
data class Branch(val key: String, val children: MutableList<JsonNode> = mutableListOf()) : JsonNode()

fun parseJson(json: String, inverted: Boolean = false): JsonNode {
    val trimmed = json.trim().removePrefix("{").removeSuffix("}")
    val parts = trimmed.split(",(?![^{]*})".toRegex())

    val root = Branch("root")

    for (part in parts) {
        val kv = part.trim().split(":")
        if (kv.size < 2) continue
        val key = kv[0].trim().removeSurrounding("\"")
        val value = kv[1].trim().removeSurrounding("\"")

        val isAllCaps = key == key.uppercase() && key.isNotEmpty() && key.all { it.isLetter() }
        val shouldInvert = isAllCaps || inverted

        if (shouldInvert) {
            val leaf = Leaf(key, value)
            val newBranch = Branch(value, mutableListOf(leaf))
            root.children.add(0, newBranch)
        } else {
            root.children.add(Leaf(key, value))
        }
    }

    return root
}

fun printTree(node: JsonNode, indent: String = "") {
    when (node) {
        is Branch -> {
            println("${indent}Branch: ${node.key}")
            node.children.forEach { printTree(it, "$indent  ") }
        }
        is Leaf -> println("${indent}Leaf: ${node.key} = ${node.value}")
    }
}

fun main() {
    val json1 = """{"name": "Alice", "AGE": "30", "city": "Cebu"}"""
    val json2 = """{"ROLE": "admin", "status": "active"}"""

    println("JSON 1:")
    printTree(parseJson(json1))
    println("\nJSON 2:")
    printTree(parseJson(json2))
}