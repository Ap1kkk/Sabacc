package ru.ngtu.sabacc.gamecore.state

data class GameState(
    var currentPlayerId: Long,
    var turn: Int,
    var round: Int
)
