package ru.ngtu.sabacc.gamecore.exception

import ru.ngtu.sabacc.gamecore.GameSession

class GameSessionNotStartedException(
    gameSession: GameSession
) : RuntimeException(
    "$gameSession hasn't started yet"
)
