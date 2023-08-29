package com.apper.dismath

import com.apper.dismath.dataclass.LegalMove
import com.apper.dismath.dataclass.Piece
import com.apper.dismath.dataclass.Tile

fun initializeBoard(): Array<Array<Piece?>> {
    val board = Array(8) { arrayOfNulls<Piece?>(8) }
    for (y in 0 until 8) {
        for (x in 0 until 8) {
            if ((x + y) % 2 == 1) {
                when (y) {
                    in 0..2 -> board[y][x] = Piece(
                        value = "F", x = x, y = y
                    )
                    in 5..7 -> board[y][x] = Piece(
                        value = "T", x = x, y = y
                    )
                }
            }
        }
    }
    return board
}

fun initializeOperations(): List<List<String>> {
    return listOf(
        listOf("", "⇐", "", "↑", "", "⊻", "", "¬"),
        listOf("⊻", "", "↑", "", "∨", "", "⇐", ""),
        listOf("", "↓", "", "∨", "", "⇔", "", "⇐"),
        listOf("↓", "", "∧", "", "⇔", "", "∨", ""),
        listOf("", "∧", "", "⇔", "", "∨", "", "↑"),
        listOf("⇐", "", "⇔", "", "∧", "", "↑", ""),
        listOf("", "⇐", "", "∧", "", "↓", "", "⊻"),
        listOf("¬", "", "⊻", "", "↓", "", "⇐", "")
    )
}

fun getLegalMoves(piece: Piece, board: Array<Array<Piece?>>, operations: List<List<String>>): List<Tile> {
    val captureMoves = mutableListOf<Tile>()
    val nonCaptureMoves = mutableListOf<Tile>()

    val directions = if (piece.isDama) {
        listOf(
            LegalMove(-1, -1),
            LegalMove(1, -1),
            LegalMove(-1, 1),
            LegalMove(1, 1)
        )
    } else {
        if (piece.value == "T") {
            listOf(LegalMove(-1, -1), LegalMove(1, -1))
        } else {
            listOf(LegalMove(-1, 1), LegalMove(1, 1))
        }
    }

    directions.forEach { dir ->
        val newY = piece.y + dir.y
        val newX = piece.x + dir.x

        if (newX in 0..7 && newY in 0..7) {
            val nextPiece = board[newY][newX]
            val operation = operations[newY][newX]

            if (nextPiece == null) {
                nonCaptureMoves.add(
                    Tile(
                        image = 0,
                        type = (newX + newY + 2) % 2,
                        isPiece = false,
                        x = newX,
                        y = newY,
                        operand = operation
                    )
                )
            } else if (nextPiece.value != piece.value) {
                val captureY = newY + dir.y
                val captureX = newX + dir.x

                if (captureX in 0..7 && captureY in 0..7 && board[captureY][captureX] == null) {
                    captureMoves.add(
                        Tile(
                            image = 0,
                            type = (captureX + captureY + 2) % 2,
                            isPiece = false,
                            x = captureX,
                            y = captureY,
                            operand = operations[captureY][captureX]
                        )
                    )
                }
            }
        }
    }

    return captureMoves.ifEmpty { nonCaptureMoves }
}

object ImageMapper {
    fun getImageResource(value: String): Int {
        return when(value) {
            "T" -> R.drawable.piece_true_normal
            "F" -> R.drawable.piece_false_normal
            else -> R.drawable.transparent_image
        }
    }
}
