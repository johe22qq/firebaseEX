package com.example.firebase

data class Player(
    var playerID: String = "",
    var name: String = "",
    var status: String = "",
    var score: Int = 0

    )
fun addScore(score: Int){

    // om en spelare vinner så ska den adda score till databasen kopplat till den personen,
    // då kan vi senare visa leaderboard vem som vunnit flest matcher
}
