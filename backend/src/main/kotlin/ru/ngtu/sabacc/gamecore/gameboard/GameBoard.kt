package ru.ngtu.sabacc.gamecore.gameboard

import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType

class GameBoard {

    private val sandDeck: MutableList<Card> = mutableListOf()
    private val bloodDeck: MutableList<Card> = mutableListOf()
    private val sandDiscard: MutableList<Card> = mutableListOf()
    private val bloodDiscard: MutableList<Card> = mutableListOf()

    init {
        for (value in 1..6) {
            sandDeck.add(Card.ValueCard(
                CardType.SAND, value
            ))
            bloodDeck.add(Card.ValueCard(
                CardType.BLOOD, value
            ))
        }

        for (i in 1..3) {
            sandDeck.add(Card.ImposterCard(
                CardType.SAND
            ))
            bloodDeck.add(Card.ImposterCard(
                CardType.BLOOD
            ))
        }

        sandDeck.add(Card.SylopCard(
            CardType.SAND
        ))
        bloodDeck.add(Card.SylopCard(
            CardType.BLOOD
        ))

        sandDeck.shuffle()
        bloodDeck.shuffle()
    }

    fun takeCard(cardType: CardType): Card {
        return when(cardType) {
            CardType.SAND -> sandDeck.removeLast()
            CardType.BLOOD -> bloodDeck.removeLast()
        }
    }

    fun takeDiscardCard(cardType: CardType): Card {
        return when(cardType) {
            CardType.SAND -> sandDiscard.removeLast()
            CardType.BLOOD -> bloodDiscard.removeLast()
        }
    }

    fun discardCard(card: Card) {
        when(card.cardType) {
            CardType.SAND -> sandDiscard.add(card)
            CardType.BLOOD -> bloodDiscard.add(card)
        }
    }
}
