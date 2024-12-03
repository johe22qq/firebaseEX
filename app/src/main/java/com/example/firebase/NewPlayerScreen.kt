package com.example.firebase

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun NewPlayerScreen(navController: NavController, model: GameModel) {

    //-------------------------NAVIGER TILL SKÄRMEN OM DU REDAN HAR EN SESSION----------------------------------------
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
    val savedPlayerId = sharedPreferences.getString("playerId", null)

    LaunchedEffect(savedPlayerId) {
        if (!savedPlayerId.isNullOrEmpty()) {
            model.localPlayerId.value = savedPlayerId
            navController.navigate("LobbyScreen")
        }
    }
    //---------------------------------------------------------------------------------------------------------------

    var playerName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()

    )
    Image(
        painter = painterResource(id = R.drawable.blue),
        contentDescription = "bakrund",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(19.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ENTER YOUR NAME!")

        Spacer(modifier = Modifier.height(19.dp))

        OutlinedTextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("YOUR NAME:") },
            modifier = Modifier

                .padding(80.dp)

        )
        Button(
            onClick = {
                if (playerName.isNotBlank()) {
                    model.db.collection("players")
                        .whereEqualTo("name", playerName)
                        .get()
                        .addOnSuccessListener { arg ->
                            val PlayerEXIST = arg.documents.firstOrNull()


                            if (PlayerEXIST != null) {
                                model.localPlayerId.value = PlayerEXIST.id // detta gör jag för att fylla på databasen
                                //---------------------------SESSION-SPARANDE-----med reservation för om jag har tänkt rätt----------------------------------------
                                val sharedPreferences = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
                                sharedPreferences.edit().putString("playerId", PlayerEXIST.id).apply() // men jag sparar även en session med idt
                                //---------------------------SESSION-SPARANDE---------------------------------------------
                                navController.navigate("LobbyScreen")
                            } else {
                                val newPlayerId = model.db.collection("players").document().id
                                val newPlayer = Player(
                                    playerID = newPlayerId,
                                    name = playerName,
                                    status = "online",
                                    score = 0
                                )
                                model.db.collection("players").document(newPlayerId).set(newPlayer)
                                    .addOnSuccessListener {
                                        model.localPlayerId.value = newPlayerId
                                        //---------------------------SESSION-SPARANDE---------------------------------------------
                                        val sharedPreferences = context.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
                                        sharedPreferences.edit().putString("playerId", newPlayerId).apply()
                                        //---------------------------SESSION-SPARANDE---------------------------------------------
                                        navController.navigate("LobbyScreen")
                                    }
                            }
                        }
                }else{
                    errorMessage = "YOU NEED A NAME TO PLAY THIS GAME, IF REFUSE YOU CAN PLAY TEMPLE RUN INSTEAD  "
                }
            },
            modifier = Modifier
                //.fillMaxWidth()
                .padding(80.dp)
        ) {
            Text("JOIN GAME")
        }

        if(errorMessage.isNotBlank()){
            Text(
                errorMessage,
                color = Color.Red

            )
        }
    }
}
