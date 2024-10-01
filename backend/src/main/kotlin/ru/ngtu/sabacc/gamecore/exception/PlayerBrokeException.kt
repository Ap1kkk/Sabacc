package ru.ngtu.sabacc.gamecore.exception

import ru.ngtu.sabacc.gamecore.GameSession
import ru.ngtu.sabacc.gamecore.player.Player

class PlayerBrokeException(
    gameSession: GameSession,
    player: Player
) : RuntimeException(
    "${player.name} don't have enough chips in $gameSession"
)
