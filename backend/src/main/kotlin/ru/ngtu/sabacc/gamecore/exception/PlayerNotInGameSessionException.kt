package ru.ngtu.sabacc.gamecore.exception

import ru.ngtu.sabacc.gamecore.GameSession
import ru.ngtu.sabacc.gamecore.player.Player

class PlayerNotInGameSessionException (
    player: Player
) : RuntimeException(
    "${player.name} not in game session"
)
