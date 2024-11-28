package com.example.firebase


import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(players) { player ->
            Text(
                text = "${player.name}: ${player.score}",
                modifier = Modifier
                    .padding(10.dp)


            )

    }



}


}




