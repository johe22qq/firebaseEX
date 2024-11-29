package com.example.firebase

import android.app.GameState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay


@Composable
fun MainScreen(navController: NavController, model: GameModel, gameId: String?) { //



    if (gameId == null) {
        return
    }

    val currentGame = remember { mutableStateOf<Game?>(null) }
    var winnerMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(gameId) {
        model.observer(gameId) { updatedGame ->
            currentGame.value = updatedGame


            val winner = DoWeHaveAWinner(updatedGame.gameBoard)
            if (winner != null) {
                val winnerId = if (winner == 1) updatedGame.player1Id else updatedGame.player2Id
                val winnerName = model.playerMap.value[winnerId]?.name
                model.db.collection("games").document(gameId).update(
                    mapOf(
                        "gameState" to "ended",
                        "winnerID" to winnerId
                    )
                )
                addScore(winnerId)
                winnerMessage= "$winnerName vann!"

                model.GameEnding(navController)
                return@observer
            }

            val isDraw = updatedGame.gameBoard.all { it != 0 } // all kollar om alla i listan inte är 0, då har vi placerat ut alla och vi har ingen winnare
            if (isDraw) {
                model.db.collection("games").document(gameId).update(
                    mapOf(
                        "gameState" to "ended",
                        "winnerID" to "draw"
                    )
                )
                winnerMessage ="oavgjort, bra spelat! "
                model.GameEnding(navController)


                //
            }
        }

    }

    Image(
        painter = painterResource(id = R.drawable.bord),
        contentDescription = "bord",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    if (!winnerMessage.isNullOrBlank()) {
        Text(
            text = winnerMessage!!,
            color = Color.Green,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top=30.dp)
                .padding(start =120.dp)

        )
    }

    currentGame.value?.let { game ->

        val currentPlayerName = model.playerMap.value[game.currentPlayerID]?.name

        Text(
            text = "$currentPlayerName s tur",
            modifier = Modifier.padding(18.dp)
        )
    }

    val cells = List(42) { it }
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .size(1000.dp)
            .padding(top = 260.dp, start = 14.dp, end = 14.dp),
    ) {
        items(cells) { cell ->
            Box(
                modifier = Modifier
                    .size(70.dp)
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












