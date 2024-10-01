package ru.ngtu.sabacc.gamecore.card

enum class CardType {
    BLOOD, SAND
}

sealed interface Card {

    val cardType: CardType

    data class ImposterCard(
        override val cardType: CardType
    ) : Card

    data class SylopCard(
        override val cardType: CardType
    ) : Card

    data class ValueCard(
        override val cardType: CardType,
        val value: Int
    ) : Card
}
