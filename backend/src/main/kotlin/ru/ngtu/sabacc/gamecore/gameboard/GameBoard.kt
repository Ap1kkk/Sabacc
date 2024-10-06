package ru.ngtu.sabacc.gamecore.gameboard

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType

data class GameBoard(
    val sandDeck: MutableList<Card>,
    val bloodDeck: MutableList<Card>,
    val sandDiscardDeck: MutableList<Card>,
    val bloodDiscardDeck: MutableList<Card>
)
