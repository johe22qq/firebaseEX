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
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavHostController, model: GameModel) { // inkuderat Nav, måste ha en gamemodell

    val games by model.gameMap.collectAsStateWithLifecycle()

// vi behöver hämta spelarens id i nån currentvariabel och matcha databasens id,  inspiererad av lab5 ex
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
            .getString("playerId", "")

    if (!sharedPreferences.isNullOrEmpty()) {
        model.localPlayerId.value = sharedPreferences
    }

    var opponentName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }


    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(25.dp)

            ) {
                Text("ENTER OPONANTS NAME")
                OutlinedTextField(
                    value = opponentName,
                    onValueChange = { opponentName = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (opponentName.isNotBlank()) {
                            val opponent =
                                model.playerMap.value.entries.find { it.value.name == opponentName }
                            if (opponent != null) {

                                val gameID = model.localGameId.value
                                if (gameID == null) {
                                    model.localGameId.value =
                                        model.db.collection("games").document().id
                                }
                                val VisiblegameID = model.localGameId.value!!

                                model.db.collection("games").add(
                                    Game(
                                        gameState = "invite",
                                        player1Id = model.localPlayerId.value!!,
                                        player2Id = opponent.key,
                                        gameId = VisiblegameID
                                    )
                                )
                            }
                        } else {
                            errorMessage = "DET FINNS INGEN SPELARE MED DET NAMNET "
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SEND CHALLENGE")
                }

//------------------------------------------ANVÄNDER KODEN IFRÅN LEADERBOARD---------------------------------------
                DisplayAllPlayers(navController)
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
                        Text("CHALLENGE FROM: ${model.playerMap.value[game.player1Id]?.name}")
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
                        modifier = Modifier.padding(top=300.dp, start = 20.dp, end = 20.dp)

                    )
            }

        }

    )

}

//samma kod som i leaderbord fast utan points
@Composable
fun DisplayAllPlayers(navController: NavController) {


    var players by remember { mutableStateOf<List<Player>>(emptyList()) }

    LaunchedEffect(Unit) {
        GetAllPlayers() { listOFplayers ->
            players = listOFplayers.sortedByDescending { it.score }

        }

    }

    Column(
        modifier = Modifier
            // .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {


        LazyColumn(
            modifier = Modifier
                // .fillMaxSize()
                .padding(16.dp)

        ) {
            items(players) { player ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(2.dp, color = androidx.compose.ui.graphics.Color.Black)
                        .background(color = LightGray)
                )

                {

                    Text(
                        text = player.name,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }
        }
    }

}


