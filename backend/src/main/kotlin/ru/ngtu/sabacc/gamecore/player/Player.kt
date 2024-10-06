package ru.ngtu.sabacc.gamecore.player

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.token.Token

data class Player(
    val tokens: MutableList<Token>,
    var remainChips: Int,
    var spentChips: Int,
    val bloodCards: MutableList<Card>,
    val sandCards: MutableList<Card>
)
