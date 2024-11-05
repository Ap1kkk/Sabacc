package ru.ngtu.sabacc.gamecore.card

enum class CardType {
    BLOOD, SAND
}

enum class CardValueType {
    IMPOSTER, SYLOP, VALUE_CARD
}

sealed interface Card {

    val cardType: CardType
    val cardValueType: CardValueType

    data class ImposterCard(
        override val cardType: CardType
    ) : Card {
        override val cardValueType: CardValueType = CardValueType.IMPOSTER
    }

    data class SylopCard(
        override val cardType: CardType
    ) : Card {
        override val cardValueType: CardValueType = CardValueType.SYLOP
    }

    data class ValueCard(
        override val cardType: CardType,
        val value: Int
    ) : Card {
        override val cardValueType: CardValueType = CardValueType.VALUE_CARD
    }
}
