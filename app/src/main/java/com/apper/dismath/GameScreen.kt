package com.apper.dismath

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Creates a copy of the board (2D array)
fun Array<Array<Piece?>>.deepCopy(): Array<Array<Piece?>> {
    return Array(this.size) { i ->
        Array(this[i].size) { j ->
            this[i][j]?.copy()
        }
    }
}

@Composable
fun GameScreen(board: Array<Array<Piece?>>, operations: List<List<String>>) {
    var currentBoard by remember { mutableStateOf(board) }

    // Move piece logic
    fun movePiece(from: Tile, to: Tile) {
        val newBoard = currentBoard.deepCopy()
        val temp = newBoard[from.y][from.x]
        newBoard[from.y][from.x] = newBoard[to.y][to.x]
        newBoard[to.y][to.x] = temp
        currentBoard = newBoard
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // App banner
        Banner()

        // Player B
        PlayerPlacard(player = "Player B", imageUrl = "")

        // Board
        Box(
            // Takes the majority of the space
            // modifier = Modifier.weight(1f)
        ) {
            Board(
                board = currentBoard,
                operations = operations,
                onMovePiece = ::movePiece
            )
        }

        // Player A
        PlayerPlacard(player = "Player A", imageUrl = "")
    }
}

@Composable
fun Banner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {}) {
            Text(text = "Button")
        }

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Dismath Logo",
            modifier = Modifier.size(40.dp)
        )
        Text(text = "Score: XDD")
    }
}

@Composable
// TODO: imageUrl should be randomized after I implemented my random name generator
fun PlayerPlacard(player: String, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = player,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = player)
    }
}
