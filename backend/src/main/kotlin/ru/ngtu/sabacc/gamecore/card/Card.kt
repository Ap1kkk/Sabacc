package ru.ngtu.sabacc.gamecore.card

enum class CardValueType {
    IMPOSTER, SYLOP, VALUE_CARD
}

sealed interface Card {

    val cardValueType: CardValueType

    data class ImposterCard(
        override val cardValueType: CardValueType = CardValueType.IMPOSTER
    ) : Card

    data class SylopCard(
        override val cardValueType: CardValueType = CardValueType.SYLOP
    ) : Card

    data class ValueCard(
        val value: Int,
        override val cardValueType: CardValueType = CardValueType.VALUE_CARD
    ) : Card
}
