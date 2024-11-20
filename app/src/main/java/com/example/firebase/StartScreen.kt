package com.example.firebase

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@Composable
fun StartScreen(navController: NavController) {
    Image(

        painter = painterResource(R.drawable.startbild),
        contentDescription = "bild på tictac",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Text(
        "TicTacToe",
        modifier = Modifier.padding(start = 110.dp, top = 70.dp),
        style = MaterialTheme.typography.headlineLarge
    )
//delvis samam kod som i lobby, ska ändras
    Button(

        onClick = {

            navController.navigate("LobbyScreen")

        },
        modifier = Modifier
            .padding(start = 145.dp)
            .padding(top = 150.dp)

    ) {

        Text("Gå till lobbyn")
    }
}