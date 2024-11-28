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
                        .addOnSuccessListener { querySnapshot ->
                            val PlayerEXIST = querySnapshot.documents.firstOrNull()

                            if (PlayerEXIST != null) {
                                model.localPlayerId.value = PlayerEXIST.id
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
                                        navController.navigate("LobbyScreen")
                                    }
                            }
                        }
                }else{
                    errorMessage = "DU MÅSTE HA ETT NAMN FÖR ATT SPELA DETTA SPEL, PASSAR DET INTE KAN DU SPELA TEMPLE-RUN "
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
