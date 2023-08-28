package com.apper.dismath

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp

object ImageMapper {
    fun getImageResource(value: String): Int {
        return when(value) {
            "T" -> R.drawable.piece_true_normal
            "F" -> R.drawable.piece_false_normal
            else -> R.drawable.transparent_image
        }
    }
}

@Composable
fun Board(
    board: Array<Array<Piece?>>,
    operations: List<List<String>>,
    onMovePiece: (from: Tile, to: Tile) -> Unit
) {
    // Initialize the board
    val tiles = operations.flatten().mapIndexed { index, operation ->
        val x = index % 8
        val y = index / 8
        val piece = board[y][x]
        val isPiece = piece != null
        Tile(
            image = if(isPiece) ImageMapper.getImageResource(piece?.value ?: "") else 0,
            type = (x + y + 2) % 2,
            isPiece = isPiece,
            x = x,
            y = y,
            operand = operation,
            piece = piece
        )
    }

    // When a piece is selected
    val selectedTile = remember { mutableStateOf<Tile?>(null) }

    LazyVerticalGrid(columns = GridCells.Fixed(8)) {
        items(tiles.size) { index ->
            val tile = tiles[index]
            SelectableTile(
                tile = tile,
                isSelected = tile == selectedTile.value,
                onSelected = { selected ->
                    Log.d("SelectableTile", "Tile selected: $selected")
                    if (selectedTile.value == null) {
                        selectedTile.value = selected
                        Log.d("SelectableTile", "Selected tile set to: $selected")
                    } else {
                        onMovePiece(selectedTile.value!!, selected!!)
                        Log.d("SelectableTile", "Move piece from ${selectedTile.value} to $selected")
                        selectedTile.value = null
                    }
                },
                onDestinationSelected = { destination ->
                    Log.d("SelectableTile", "Destination tile selected: $destination")
                    if (selectedTile.value != null) {
                        onMovePiece(selectedTile.value!!, destination)
                        Log.d("SelectableTile", "Move piece from ${selectedTile.value} to $destination")
                        selectedTile.value = null
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
        tile.copy(isSelected = isSelected).content()
    }
}


