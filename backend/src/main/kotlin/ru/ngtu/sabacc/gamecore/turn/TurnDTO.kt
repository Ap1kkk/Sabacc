package ru.ngtu.sabacc.gamecore.turn

data class TurnDTO(
    val sessionId: Long,
    val playerId: Long,
    val moveType: MoveType
)
