package ru.ngtu.sabacc.gamecore.board

import ru.ngtu.sabacc.gamecore.card.Card

data class Board(
    val sandDeck: MutableList<Card>,
    val bloodDeck: MutableList<Card>,
    val sandDiscardDeck: MutableList<Card>,
    val bloodDiscardDeck: MutableList<Card>
)
