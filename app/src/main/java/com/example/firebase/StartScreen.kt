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

@Composable
fun StartScreen(navController: NavController) {
    Text(
        "Henrys Spel",
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
            .padding(top = 310.dp)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
            contentDescription = "starta spel knapp",
            modifier = Modifier.size(90.dp)
        )
    }
}