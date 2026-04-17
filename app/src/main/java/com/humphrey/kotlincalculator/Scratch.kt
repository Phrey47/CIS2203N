package com.humphrey.kotlincalculator

data class Position(val x: Int, val y: Int)

class Board(val size: Int = 8) {
    fun isValid(pos: Position) = pos.x in 0 until size && pos.y in 0 until size
}

class TurnCounter(var turn: Int = 0) {
    fun next() { turn++ }
}

class GravityState(var inverted: Boolean = false)

context(board: Board, counter: TurnCounter, gravity: GravityState)
fun Position.knightMoves(): List<Position> {
    val baseMoves = listOf(
        Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
        Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
    )

    val moves = if (gravity.inverted) {
        baseMoves.map { (dx, dy) -> Pair(-dx, -dy) }
    } else {
        baseMoves
    }

    counter.next()
    return moves.map { (dx, dy) -> Position(x + dx, y + dy) }
        .filter { board.isValid(it) }
}

fun main() {
    val board = Board()
    val turnCounter = TurnCounter()
    val gravity = GravityState()

    with(board) {
        with(turnCounter) {
            with(gravity) {
                val pos = Position(3, 3)
                println("Normal moves from $pos:")
                println(pos.knightMoves())

                gravity.inverted = true
                println("Inverted gravity moves from $pos:")
                println(pos.knightMoves())

                println("Total turns: ${turnCounter.turn}")
            }
        }
    }
}
