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
        composable("LeaderboardScreen") { LeaderboardScreen(model,navController) }


        composable("MainScreen/{gameId}") { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            MainScreen(navController,model,gameId)

        }// Eventuellt även ifrån MainScreen beroende på om vi gör fler fönster eller vill kunna komam tillbaka
        composable("StartScreen") { StartScreen(navController) }
        composable("NewPlayerScreen") { NewPlayerScreen(navController,model) }
// lust nu ingen navController i mainscreen, (test)
    }
}
@Composable
fun StartTheGame(){
    val gameModel = remember { GameModel() }
    gameModel.initGame()
    val navController = rememberNavController()
    ScreenNavigation(navController = navController, model = gameModel)
}