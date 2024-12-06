package com.example.firebase

import android.app.GameState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

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
import androidx.compose.ui.graphics.Color.Companion.Black
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
                winnerMessage= " AND THE WINNER IS.. $winnerName "

                model.GameEnding(navController)

            }

            val isDraw = updatedGame.gameBoard.all { it != 0 } // all kollar om alla i listan inte är 0, då har vi placerat ut alla och vi har ingen winnare
            if (isDraw) {
                model.db.collection("games").document(gameId).update(
                    mapOf(
                        "gameState" to "ended",
                        "winnerID" to "draw"
                    )
                )
                winnerMessage ="TIE "
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

        Column (

            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

        Text(
            text = winnerMessage!!,
            color = Black,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top=800.dp)
                .border(3.dp, color=Black)
                .background(color = Color.Green)
        )
    }
    }

    currentGame.value?.let { game ->

        val currentPlayerName = model.playerMap.value[game.currentPlayerID]?.name
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Text(
            text = "$currentPlayerName:S TURN",
            fontSize = 25.sp,
            modifier = Modifier
                .padding(18.dp),
        )
    }
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
    val column = cell % 7 // tex 16%7 =2
    val updatedBoard = game.gameBoard.toMutableList()

    var row = 5// bottemrad
    while (row >= 0) { // från botten till toppen
        val index = row * 7 + column // hoppar till rätt rad , tex 5 *7 +2 = 37e cellen
        if (updatedBoard[index] == 0) {
            updatedBoard[index] = if (game.currentPlayerID == game.player1Id) 1 else 2

            val nextPlayerId = if (game.currentPlayerID == game.player1Id) game.player2Id else game.player1Id
            val nextGameState = if (game.currentPlayerID == game.player1Id) "player2_turn" else "player1_turn"

            model.updateBoard(gameId, updatedBoard, nextPlayerId, nextGameState)
            break
        }
        row--
    }
    /*
    0  1  2  3  4  5   6
    7  8  9  10 11 12 13
    14 15 16 17 18 09 20
    21 22 23 24 25 26 27
    28 29 30 31 32 33 34
    35 36 37 38 39 40 41
     */
}









