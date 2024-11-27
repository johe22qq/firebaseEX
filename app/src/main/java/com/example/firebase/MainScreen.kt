package com.example.firebase

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController, model: GameModel, gameId: String?) { //



    Image(
        painter = painterResource(id = R.drawable.bord),
        contentDescription = "bord",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop)

    val cells = List(42) { it }
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier
            .size(1000.dp)
            .padding(top = 230.dp, start = 14.dp, end = 14.dp),
    ) {
        items(cells) { cell ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        //
                    }
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trabakrund),
                    contentDescription = "trabackrund",
                    modifier = Modifier.fillMaxSize(),
                    // Modifier.size(20.dp)
                )
            }
        }
    }
}
fun clickHandler()




