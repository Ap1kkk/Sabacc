package ru.ngtu.sabacc.gamecore.exception

import ru.ngtu.sabacc.gamecore.GameSession
import ru.ngtu.sabacc.gamecore.player.Player

class WrongPlayerMoveException(
    gameSession: GameSession,
    player: Player
) : RuntimeException(
    "${player.name} made the wrong move in $gameSession"
)
