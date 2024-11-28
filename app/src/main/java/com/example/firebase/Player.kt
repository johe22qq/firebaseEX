package com.example.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Player(
    var playerID: String = "",
    var name: String = "",
    var status: String = "",
    var score: Int = 0

    )


fun addScore(playerId: String) {

    val player = Firebase.firestore.collection("players").document(playerId)
    player.get().addOnSuccessListener { document ->
        if (document != null && document.exists()) {
            val currentScore = document.getLong("score")?.toInt() ?: 0 // i databasen lagras "number" som en long
            player.update("score", currentScore + 1)
        }
    }
}