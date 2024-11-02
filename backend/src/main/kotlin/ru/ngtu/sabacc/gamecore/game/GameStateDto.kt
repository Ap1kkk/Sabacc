package ru.ngtu.sabacc.gamecore.game

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.player.Player

data class GameStateDto(
    val currentPlayerId: Long? = null,
    val round: Int? = null,
    val bloodDiscard: Card? = null,
    val sandDiscard: Card? = null,
    val players: List<Player>? = null
)
