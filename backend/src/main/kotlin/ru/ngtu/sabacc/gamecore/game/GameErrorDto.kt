package ru.ngtu.sabacc.gamecore.game

class GameErrorDto(
    val sessionId: Long? = null,
    val playerId: Long? = null,
    val errorType: GameErrorType? = null,
    val details: Map<String, Any>? = null
)
