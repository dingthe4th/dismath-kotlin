package com.apper.dismath.dataclass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Tile(
    val image: Int,
    val type: Int,
    val isPiece: Boolean,
    val x: Int,
    val y: Int,
    val operand: String,
    val piece: Piece? = null,
    val isSelected: Boolean = false,
    val isLegalMove: Boolean = false
)  {
    @Composable
    fun content() {
        val tileColor = when {
            isSelected -> Color(0xFFff5f00)
            isLegalMove -> Color.DarkGray // (0xFFffeb00)
            type % 2 == 1 -> Color(0xFF779556)
            else -> Color(0xFFebecd0)
        }
        val textColor = if (isPiece) Color.Transparent else if (type % 2 == 1) Color(0xFFebecd0) else Color(0xFF202020)

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(tileColor)
        ) {
            if (isPiece && image != 0) {
                val painter = painterResource(id = image)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = operand,
                color = textColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}