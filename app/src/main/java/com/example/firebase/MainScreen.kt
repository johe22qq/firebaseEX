package com.example.firebase

import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {

    val cells = List(42) { it }
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        Modifier.padding(top = 59.dp, start = 10.dp, end = 10.dp),
    ) {
        items(cells) { cell ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trabakrund),
                    contentDescription = "trabackrund",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

