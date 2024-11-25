package com.example.firebase

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavHostController, model: GameModel) { // inkuderat Nav, måste ha en gamemodell

    val games by model.gameMap.collectAsStateWithLifecycle()

// vi behöver hämta spelarens id i nån currentvariabel och matcha databasens id,  inspiererad av lab5 ex
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
            .getString("playerId", "")

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

                                model.db.collection("games").add(
                                    Game(
                                        gameState = "invite",
                                        player1Id = model.localPlayerId.value!!,
                                        player2Id = opponent.key
                                    )
                                )
                            } else {
                                errorMessage =" user does not exist "
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SEND CHALLENGE")
                }

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
                                    .update("gameState", "player1_turn")
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
        }
    )
}

    /*

    behöver göra om denna helt,
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(players) { player ->
                ListItem(
                    headlineContent = {
                        Text("Name: ${player.name}")
                    },
                    supportingContent = {
                        Text("Status: ${player.status}")
                    },
                    trailingContent = {
                        Button(onClick = {
                            val query = db.collection("players").whereEqualTo("playerID", player.playerID)
                            query.get().addOnSuccessListener { querySnapshot ->
                                for (documentSnapshot in querySnapshot) {
                                    if (player.status == "online") {
                                        documentSnapshot.reference.update("status", "offline")
                                    } else {
                                        documentSnapshot.reference.update("status", "online")
                                    }
                                }
                            }


                        }) {
                            Text("UPPDATE STATUS")
                        }
                    }
                )
            }
        }

        if(!AreOnline.value){
            Text(
                text = "Waiting for both player to connect......",
                modifier = Modifier.padding(top = 200.dp, start = 16.dp)
            )
        }
        if(AreOnline.value){
            Button(

                onClick = {

                    navController.navigate("MainScreen") // nu kan vi navigera

                },
                modifier = Modifier
                    .padding(start = 145.dp)
                    .padding(top = 310.dp)

            ) {
                Icon(
                    painter = painterResource(id= R.drawable.baseline_play_arrow_24),
                    contentDescription ="starta spel knapp",
                    modifier = Modifier.size(90.dp)
                )
               // Text("STARTA SPELET")

            }
        }
    }
}
/*
     */