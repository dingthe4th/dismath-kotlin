package com.apper.dismath

data class Piece(
    val value: String,
    val x: Int,
    val y: Int,
    val isPiece: Boolean = true,
    val isDama: Boolean = false
) {
    fun copy(): Piece {
        return Piece(value, x, y, isPiece, isDama)
    }
}

