package com.example.firebase

import androidx.compose.runtime.Composable
import kotlinx.coroutines.delay


fun DoWeHaveAwinner(board: List<Int>): Int? {

    val ValdidWinMoves = listOf(
        listOf(0, 1, 2, 3), listOf(1, 2, 3, 4), listOf(2, 3, 4, 5), listOf(3, 4, 5, 6),
        listOf(7, 8, 9, 10), listOf(8, 9, 10, 11), listOf(9, 10, 11, 12), listOf(10, 11, 12, 13),
        listOf(14, 15, 16, 17), listOf(15, 16, 17, 18), listOf(16, 17, 18, 19), listOf(17, 18, 19, 20),
        listOf(21, 22, 23, 24), listOf(22, 23, 24, 25), listOf(23, 24, 25, 26), listOf(24, 25, 26, 27),
        listOf(28, 29, 30, 31), listOf(29, 30, 31, 32), listOf(30, 31, 32, 33), listOf(31, 32, 33, 34),
        listOf(35, 36, 37, 38), listOf(36, 37, 38, 39), listOf(37, 38, 39, 40), listOf(38, 39, 40, 41),

        listOf(0, 7, 14, 21), listOf(7, 14, 21, 28), listOf(14, 21, 28, 35), listOf(1, 8, 15, 22),
        listOf(8, 15, 22, 29), listOf(15, 22, 29, 36), listOf(2, 9, 16, 23), listOf(9, 16, 23, 30),
        listOf(16, 23, 30, 37), listOf(3, 10, 17, 24), listOf(10, 17, 24, 31), listOf(17, 24, 31, 38),
        listOf(4, 11, 18, 25), listOf(11, 18, 25, 32), listOf(18, 25, 32, 39), listOf(5, 12, 19, 26),
        listOf(12, 19, 26, 33), listOf(19, 26, 33, 40), listOf(6, 13, 20, 27), listOf(13, 20, 27, 34),
        listOf(20, 27, 34, 41),

        listOf(0, 7, 14, 21), listOf(1, 8, 15, 22), listOf(2, 9, 16, 23),
        listOf(5, 10, 15, 20), listOf(4, 9, 14, 21), listOf(3, 8, 13, 20),

        listOf(6, 13, 20, 27), listOf(7, 14, 16, 23), listOf(8, 15, 17, 24),
        listOf(11, 16, 21, 26), listOf(10, 15, 20, 25), listOf(10, 14, 19, 24),







    )

    for (wins in ValdidWinMoves) {
        val (a, b, c, d) = wins
        if (board[a] != 0 && board[a] == board[b] && board[a] == board[c] && board[a] == board[d]) {

            return board[a]
        }
    }

    return null
}


