package com.example.firebase

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun LobbyScreen() {
    val db = Firebase.firestore
    val playerList = remember { MutableStateFlow<List<Player>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        db.collection("players").addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val players = value.toObjects(Player::class.java)
                coroutineScope.launch {
                    playerList.emit(players)
                }
            }
        }
    }

    val players by playerList.collectAsStateWithLifecycle()

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
    }
}