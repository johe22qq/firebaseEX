package com.example.firebase

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavHostController) { // inkuderat Nav, måste ha en gamemodell


// vi behöver hämta spelarens id i nån currentvariabel och matcha databasens id,  inspiererad av lab5 ex
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
            .getString("playerId", "")

    var opponentName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }


//BEHÖVER SKAPA DÄR MAN KAN ANGE MOTSTÅNDARENAMN OCH ATT MAN KAN UTMANA!

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

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (opponentName.isNotBlank()) {
                            // fixa det praktiska med att skicka en chalange

                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SEND CHALLENGE")
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