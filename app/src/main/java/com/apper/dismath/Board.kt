package com.apper.dismath

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apper.dismath.dataclass.Piece
import com.apper.dismath.dataclass.Tile

@Composable
fun Board(
    board: Array<Array<Piece?>>,
    operations: List<List<String>>,
    onMovePiece: (from: Tile, to: Tile) -> Unit
) {
    // When a piece is selected
    val selectedTile = remember { mutableStateOf<Tile?>(null) }
    // Stores the legal moves
    val legalMoves = remember { mutableStateOf<List<Tile>>(emptyList()) }

    // Initialize the board
    val tiles = operations.flatten().mapIndexed { index, operation ->
        val x = index % 8
        val y = index / 8
        val piece = board[y][x]
        val isPiece = piece != null
        Tile(
            image = if (isPiece) ImageMapper.getImageResource(piece?.value ?: "") else 0,
            type = (x + y + 2) % 2,
            isPiece = isPiece,
            x = x,
            y = y,
            operand = operation,
            piece = piece
        )
    }

    LazyVerticalGrid(columns = GridCells.Fixed(8)) {
        items(tiles.size) { index ->
            val tile = tiles[index]
            val isTileSelected = tile == selectedTile.value
            val isLegalMove = tile in legalMoves.value
            SelectableTile(
                tile = tile,
                isSelected = isTileSelected,
                isLegalMove = isLegalMove,
                onSelected = { selected ->
                    Log.d("SelectableTile", "Tile selected: $selected")
                    if (selectedTile.value == null) {
                        selectedTile.value = selected
                        legalMoves.value = getLegalMoves(selected!!.piece!!, board, operations)
                        Log.d("SelectableTile", "Selected tile set to: $selected")
                    } else {
                        if (tile in legalMoves.value) {
                            onMovePiece(selectedTile.value!!, selected!!)
                            Log.d("BoardState", "Current Board on selected:\n${board.boardToString()}")
                            Log.d("SelectableTile", "Move piece from ${selectedTile.value} to $selected")
                        }
                        selectedTile.value = null
                        legalMoves.value = emptyList()
                    }
                },
                onDestinationSelected = { destination ->
                    Log.d("SelectableTile", "Destination tile selected: $destination")
                    if (selectedTile.value != null && destination in legalMoves.value) {
                        onMovePiece(selectedTile.value!!, destination)
                        Log.d("BoardState", "Current Board on destination:\n${board.boardToString()}")
                        Log.d("SelectableTile", "Move piece from ${selectedTile.value} to $destination")
                        selectedTile.value = null
                        legalMoves.value = emptyList()
                    }
                }
            )
        }
    }
}

@Composable
fun SelectableTile(
    tile: Tile,
    isSelected: Boolean,
    isLegalMove: Boolean,
    onSelected: (Tile?) -> Unit,
    onDestinationSelected: (Tile) -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clickable {
                if (!isSelected) {
                    onSelected(tile)
                } else {
                    onDestinationSelected(tile)
                }
            }
    ) {
        tile.copy(isSelected = isSelected, isLegalMove = isLegalMove).content()
    }
}

fun Array<Array<Piece?>>.boardToString(): String {
    val builder = StringBuilder()
    for (row in this) {
        for (piece in row) {
            builder.append(piece?.value ?: "-")
            builder.append(" ")
        }
        builder.append("\n")
    }
    return builder.toString()
}

