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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

@Composable
fun StartScreen(navController: NavController) {
    Image(

        painter = painterResource(R.drawable.startbild),
        contentDescription = "bild på tictac",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Text(
        "Connect-4",
        modifier = Modifier.padding(start = 55.dp, top = 70.dp),
        style = MaterialTheme.typography.displayLarge,

    )

    Button(
        onClick = {
            navController.navigate("LeaderboardScreen")
        },

        modifier = Modifier
            .padding(start = 120.dp)
            .padding(top = 210.dp)

    ) {

        Text(

            "LEADERBOARD",
            color = Color.Black

        )

    }


//delvis samam kod som i lobby, ska ändras

    Button(

        onClick = {

            navController.navigate("NewPlayerScreen")

        },
        modifier = Modifier
            .padding(start = 145.dp)
            .padding(top = 150.dp)

    ) {

        Text(
            "SPELA",
            color = Color.Black

        )
    }


}