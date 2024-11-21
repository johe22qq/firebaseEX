//enklare med en egen fil om jag senare vill lägga till/ändra och använda utan duplicering

package com.example.firebase

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ScreenNavigation(navController: NavHostController) {

    //val model = GameModel()

    NavHost(
        navController = navController,
        startDestination = "StartScreen"
    ) {
        composable("LobbyScreen") { LobbyScreen(navController) } // vi behöver kunna komma ifrån LobbyScreen
        composable("MainScreen") { MainScreen() }// Eventuellt även ifrån MainScreen beroende på om vi gör fler fönster eller vill kunna komam tillbaka
        composable("StartScreen") { StartScreen(navController) }
        composable("NewPlayerScreen") { NewPlayerScreen(navController) }
// lust nu ingen navController i mainscreen, (test)
    }
}

