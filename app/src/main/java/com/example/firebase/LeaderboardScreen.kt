package com.example.firebase


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun GetAllPlayers(model: GameModel, listOFplayers: (List<Player>) -> Unit) {

    val player = Firebase.firestore.collection("players")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val players = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Player::class.java)

            }
            listOFplayers(players)

        }

}

@Composable
fun LeaderboardScreen(model: GameModel) {

    var players by remember { mutableStateOf<List<Player>>(emptyList()) }

    LaunchedEffect(Unit) {
        GetAllPlayers(model) { listOFplayers ->
            players = listOFplayers

        }
    }

}