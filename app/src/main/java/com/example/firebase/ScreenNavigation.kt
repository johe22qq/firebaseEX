//enklare med en egen fil om jag senare vill lägga till/ändra och använda utan duplicering

package com.example.firebase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ScreenNavigation(navController: NavHostController, model: GameModel) {

    NavHost(
        navController = navController,
        startDestination = "StartScreen"
    ) {
        composable("LobbyScreen") { LobbyScreen(navController,model) }
        composable("LeaderboardScreen") { LeaderboardScreen(navController) }
        composable("MainScreen/{gameId}") { arg ->
            val gameId = arg.arguments?.getString("gameId")
            MainScreen(navController,model,gameId)
        }
        composable("StartScreen") { StartScreen(navController) }
        composable("NewPlayerScreen") { NewPlayerScreen(navController,model) }
    }
}
@Composable
fun StartTheGame(){
    val gameModel = remember { GameModel() }
    gameModel.initGame()
    val navController = rememberNavController()
    ScreenNavigation(navController = navController, model = gameModel)
}