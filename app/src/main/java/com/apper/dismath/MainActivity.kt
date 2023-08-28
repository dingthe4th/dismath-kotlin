package com.apper.dismath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.apper.dismath.ui.theme.DismathKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DismathKotlinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val board = initializeBoard()
                    val operations = initializeOperations();

                    GameScreen(board = board, operations = operations)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    DismathKotlinTheme {
        val board = initializeBoard()
        val operations = initializeOperations();
        GameScreen(board = board, operations = operations)
    }
}
