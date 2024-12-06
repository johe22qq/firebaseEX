package com.example.firebase

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameModel: ViewModel() {
    val db = Firebase.firestore
    var localPlayerId = mutableStateOf<String?>(null)
    val playerMap = MutableStateFlow<Map<String, Player>>(emptyMap())
    val gameMap = MutableStateFlow<Map<String, Game>>(emptyMap())
    var localGameId = mutableStateOf<String?>(null)

    fun initGame() {

        db.collection("players")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (value != null) {
                    val updatedMap = value.documents.associate { associatePlayer ->
                        associatePlayer.id to associatePlayer.toObject(Player::class.java)!!
                    }
                    playerMap.value = updatedMap
                }
            }

        db.collection("games")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (value != null) {
                    val updatedMap = value.documents.associate { associateGame ->
                        associateGame.id to associateGame.toObject(Game::class.java)!!
                    }
                    gameMap.value = updatedMap
                }
            }
    }
    fun observer(gameId: String, onGameUpdated: (Game) -> Unit) { // void
        db.collection("games").document(gameId)
            .addSnapshotListener { snapshot, error -> // enskilt spel
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val game = snapshot.toObject(Game::class.java)
                    if (game != null) {
                        onGameUpdated(game)
                    }
                }
            }
    }

    fun updateBoard(gameId: String, updatedBoard: List<Int>, nextPlayerId: String,nextGameState: String) {

        db.collection("games").document(gameId)
            .update(
                mapOf(
                    "gameBoard" to updatedBoard,
                    "currentPlayerID" to nextPlayerId,
                    "gameState" to nextGameState
                )
            )
    }

    fun GameEnding(navController: NavController) {
        viewModelScope.launch {
            delay(3000) // måste ligga i en corountin och när det gör det måste jag använda viewmodelscope, asynkrona operationer
            navController.navigate("LeaderboardScreen")
        }
    }

}







