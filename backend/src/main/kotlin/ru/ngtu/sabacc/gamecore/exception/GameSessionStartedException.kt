package ru.ngtu.sabacc.gamecore.exception

import ru.ngtu.sabacc.gamecore.GameSession

class GameSessionStartedException(
    gameSession: GameSession
) : RuntimeException(
    "$gameSession already started"
)
