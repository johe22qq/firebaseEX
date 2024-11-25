package com.example.firebase

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController, model: GameModel, gameId: String?) { //

    val game = gameId?.let { model.gameMap.value[it] }

    Image(
        painter = painterResource(id = R.drawable.bord),
        contentDescription = "bord",
        modifier = Modifier.fillMaxSize(),
    )

    if(game == null){
        Text("spelet hittas ej")
        return
    }

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
    if(game.player1Id == model.localPlayerId.value){
        Text("spelare 1 tur")
    }else {
        Text("spelare 2 tur")
    }
}

