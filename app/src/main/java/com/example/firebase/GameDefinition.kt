package com.example.firebase

data class Game(
    val gameId: String = "",
    val player1Id: String = "",
    val player2Id: String = "",
    val gameState: String = "invite",
    val gameBoard: List<Int> = List(42) { 0 }
)


