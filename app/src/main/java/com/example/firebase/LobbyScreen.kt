package com.example.firebase

import android.content.Context
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavHostController, model: GameModel) {

    val games by model.gameMap.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
    val savedPlayerId = sharedPreferences.getString("playerId", "")

    LaunchedEffect(savedPlayerId) {
        if (!savedPlayerId.isNullOrEmpty()) {
            model.localPlayerId.value = savedPlayerId // här plockar jag id från sharedpref inte från databasen, pga av sessionen
        }
    }

    var opponentName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }


 //--------------------------------------------------------------------------------------------

    val playerName = remember(model.localPlayerId.value) {
        model.playerMap.value[model.localPlayerId.value]?.name
    }
//-----------------------------------------------------------------------------------------------

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(25.dp)

            ) {
//-------------------------OM NAMNET IFRÅN SESSIONEN SPARAS COREKT BÖR JAG SE MITT NAMN HÄT----------------------------------------
                Text(
                    text = "Welcome, $playerName!",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
 //--------------------------------------------------------------------------------------------------------------------------------------


                Text("ENTER OPONANTS NAME")
                OutlinedTextField(
                    value = opponentName,
                    onValueChange = { opponentName = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (opponentName.isNotBlank() && opponentName != playerName) {
                            val opponent = model.playerMap.value.entries.find { it.value.name == opponentName }
                            if (opponent != null) {

                                val gameID = model.localGameId.value ?: model.db.collection("games").document().id
                                model.localGameId.value = gameID

                                model.db.collection("games").add(
                                    Game(
                                        gameState = "invite",
                                        player1Id = model.localPlayerId.value!!,
                                        player2Id = opponent.key,
                                        gameId = gameID
                                    )
                                )
                            }
                        } else {

                            errorMessage = "THERE IS NO OPPONENT WITH THAT NAME  "
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Text("SEND CHALLENGE")
                }

//------------------------------------------ANVÄNDER KODEN IFRÅN LEADERBOARD---------------------------------------
                DisplayAllPlayers(model = model)
//---------------------------------------------------------------------------------------------------------------------------

                games.forEach { (gameId, game) ->

                    if (game.player1Id == model.localPlayerId.value || game.player2Id == model.localPlayerId.value) {
                        if (game.gameState == "player1_turn") {
                            LaunchedEffect(gameId) {
                                navController.navigate("MainScreen/$gameId")
                            }
                        }
                    }
                    if (game.player2Id == model.localPlayerId.value && game.gameState == "invite") {
                        Text(
                            "CHALLENGE FROM: ${model.playerMap.value[game.player1Id]?.name}",
                            fontSize = 25.sp
                        )
                        Button(
                            onClick = {
                                model.db.collection("games").document(gameId)
                                    .update(
                                        mapOf(
                                            "gameState" to "player1_turn",
                                            "currentPlayerID" to game.player1Id
                                        )
                                    )
                                    .addOnSuccessListener {
                                        navController.navigate("MainScreen/$gameId")
                                    }
                            }
                        ) {
                            Text("ACCEPT")
                        }
                    }
                    }
                }
                if(errorMessage.isNotBlank()){

                    Text(
                        errorMessage,
                        modifier = Modifier.padding(top=850.dp, start = 20.dp, end = 20.dp)

                    )
            }

        }

    )

}

//samma kod som i leaderbord fast utan points, kanske lite DRY varning på denna
@Composable
fun DisplayAllPlayers(model: GameModel) {

    val players by model.playerMap.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .height(500.dp)
        ) {
            items(players.values.sortedByDescending { it.score }) { player ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(2.dp, color = androidx.compose.ui.graphics.Color.Black)
                        .background(color = LightGray)
                ) {
                    Text(
                        text = player.name,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(10.dp)

                    )
                }
            }
        }
    }
}





