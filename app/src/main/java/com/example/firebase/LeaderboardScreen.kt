package com.example.firebase


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun GetAllPlayers(listOFplayers: (List<Player>) -> Unit) {

    Firebase.firestore.collection("players")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val players = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Player::class.java)

            }
            listOFplayers(players)

        }

}

@Composable
fun LeaderboardScreen(navController: NavController) {

    var players by remember { mutableStateOf<List<Player>>(emptyList()) }

    LaunchedEffect(Unit) {
        GetAllPlayers() { listOFplayers ->
            players = listOFplayers.sortedByDescending { it.score }

        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {

        Text(
            text = "LEADERBOARD",
            fontSize = 25.sp,
            modifier = Modifier
                .padding(bottom = 19.dp)
                .padding(top = 19.dp)
        )


        LazyColumn(
            modifier = Modifier
               // .fillMaxSize()
                .padding(16.dp)
                .weight(1f)


        ) {
            items(players) { player ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(2.dp, color = Color.Green)
                        .background(color = LightGray)
                )

                {

                Text(
                    text = "${player.name}: ${player.score}",
                    modifier = Modifier
                        .padding(10.dp)
                )
                }
            }
        }
        Button(
            onClick = {
                navController.navigate("LobbyScreen")
            },

        ) {
            Text(" <-- BACK TO LOBBY FOR ANOTHER ROUND <-- ")


        }
    }

}




