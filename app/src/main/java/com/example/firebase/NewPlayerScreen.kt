package com.example.firebase

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun NewPlayerScreen(navController: NavController /*, model: GameModel*/) {

    Button(
        onClick = {
            navController.navigate("LobbyScreen") // Navigate to LobbyScreen
        }
    ) {
        Text("Go to Lobby")
    }
}
