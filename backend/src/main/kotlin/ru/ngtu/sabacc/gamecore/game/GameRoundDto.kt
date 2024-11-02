package ru.ngtu.sabacc.gamecore.game

import ru.ngtu.sabacc.gamecore.player.Player

data class GameRoundDto(
    val round: Int,
    val players: List<Player>
)
