//enklare med en egen fil om jag senare vill lägga till/ändra och använda utan duplicering

package com.example.firebase

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ScreenNavigation(navController: NavHostController, model: GameModel) {

    NavHost(
        navController = navController,
        startDestination = "StartScreen"
    ) {
        composable("LobbyScreen") { LobbyScreen(navController,model) }

        composable("MainScreen/{gameId}") { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            MainScreen(navController,model,gameId)

        }// Eventuellt även ifrån MainScreen beroende på om vi gör fler fönster eller vill kunna komam tillbaka
        composable("StartScreen") { StartScreen(navController) }
        composable("NewPlayerScreen") { NewPlayerScreen(navController,model) }
// lust nu ingen navController i mainscreen, (test)
    }
}

