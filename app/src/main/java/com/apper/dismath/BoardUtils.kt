package com.apper.dismath

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
