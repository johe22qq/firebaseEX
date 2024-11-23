package com.example.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebase.ui.theme.FirebaseTheme
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseTheme {

                val gameModel = remember { GameModel() }
                gameModel.initGame()

                val navController = rememberNavController()

                ScreenNavigation(navController = navController, model = gameModel)
            }
        }
    }
}

@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(): State<T> {
    val state = remember { mutableStateOf(value) }
    LaunchedEffect(this) {
        collect { state.value = it }
    }
    return state
}



/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirebaseTheme {
        LobbyScreen()
    }
}
*/









