package com.example.firebase


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


fun GetAllPlayers(model: GameModel) {

    val player = Firebase.firestore.collection("players")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val players = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Player::class.java)

            }


        }

}

@Composable
fun LeaderboardScreen(model: GameModel) {



}