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
import kotlinx.coroutines.delay


@Composable
fun MainScreen(navController: NavController, model: GameModel, gameId: String?) { //



    if (gameId == null) {
        return
    }

    val currentGame = remember { mutableStateOf<Game?>(null) }


    LaunchedEffect(gameId) {
        model.observer(gameId) { updatedGame ->
            currentGame.value = updatedGame


            val winner = DoWeHaveAwinner(updatedGame.gameBoard)
            if (winner != null) {

                val winnerId = if (winner == 1) updatedGame.player1Id else updatedGame.player2Id// placerar utanför för att addscore ska komma åt

                model.db.collection("games").document(gameId).update(
                    mapOf(
                        "gameState" to "ended",
                        "winnerID" to winnerId
                    )
                )

                addScore(winnerId)
                navController.navigate("LeaderboardScreen")
            }

        }
    }

    Image(
        painter = painterResource(id = R.drawable.bord),
        contentDescription = "bord",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    currentGame.value?.let { game ->
        val currentPlayerName = if (game.currentPlayerID == game.player1Id) {
            game.player1Id
        } else {
            game.player2Id
        }

        Text(
            text = "$currentPlayerName tur",
            modifier = Modifier.padding(18.dp)
        )
        }

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
                        currentGame.value?.let { game ->
                            if(game.currentPlayerID == model.localPlayerId.value) {
                                clickHandler(cell, gameId, model, game)
                            }
                        }
                    }
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {

                currentGame.value?.let { game ->
                    val cellVal = game.gameBoard[cell]

                    if(cellVal == 1){
                        Image(
                            painter = painterResource(id = R.drawable.red),
                            contentDescription = "Bild på röd spelpjäls",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop

                            )
                    }else if(cellVal == 2){
                        Image(
                           painter = painterResource(id = R.drawable.blue),
                           contentDescription = "Bild på blå spelpjäs",
                           modifier = Modifier.fillMaxSize(),
                           contentScale = ContentScale.Crop

                           )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.trabakrund),
                            contentDescription = "Bild på trä",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )


                    }

                }

            }

        }

    }
}


fun clickHandler(cell: Int, gameId: String, model: GameModel, game: Game) {

    if (game.gameBoard[cell] != 0) {
        return
    }
    if (game.currentPlayerID != model.localPlayerId.value) {
        return
    }
    val updatedBoard = game.gameBoard.toMutableList()
    if (game.currentPlayerID == game.player1Id) {
        updatedBoard[cell] = 1
    } else {
        updatedBoard[cell] = 2
    }

    val nextPlayerId = if (game.currentPlayerID == game.player1Id) game.player2Id else game.player1Id
    model.updateBoard(gameId, updatedBoard, nextPlayerId)

}





