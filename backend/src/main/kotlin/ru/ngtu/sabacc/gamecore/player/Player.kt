package ru.ngtu.sabacc.gamecore.player

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.token.Token

data class Player(
    val tokens: MutableList<Token> = mutableListOf(
        Token.NO_TAX,
        Token.TAKE_TWO_CHIPS,
        Token.OTHER_PLAYERS_PAY_ONE
    ),
    var remainChips: Int = 4,
    var spentChips: Int = 0,
    val bloodCards: MutableList<Card> = mutableListOf(),
    val sandCards: MutableList<Card> = mutableListOf(),
    var handRating: Pair<Int, Int>? = null
)
