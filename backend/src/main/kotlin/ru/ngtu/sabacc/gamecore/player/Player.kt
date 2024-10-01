package ru.ngtu.sabacc.gamecore.player

import ru.ngtu.sabacc.gamecore.GameSession
import ru.ngtu.sabacc.gamecore.card.Card
import ru.ngtu.sabacc.gamecore.card.CardType
import ru.ngtu.sabacc.gamecore.exception.PlayerNotInGameSessionException
import ru.ngtu.sabacc.gamecore.token.Token

class Player(
    val name: String
) {

    val tokens: MutableList<Token> = mutableListOf()
    lateinit var chips: Chips
    lateinit var hand: Hand
    private var gameSession: GameSession? = null

    fun join(gameSession: GameSession) {
        leave()

        gameSession.join(this)
        this.gameSession = gameSession

        tokens.add(
            Token.NO_TAX
        )
        tokens.add(
            Token.TAKE_TWO_CHIPS
        )
        tokens.add(
            Token.OTHER_PLAYERS_PAY_ONE
        )
    }

    fun leave() {
        gameSession?.leave(this)
        gameSession = null
    }

    fun takeCard(cardType: CardType) {
        if (gameSession == null)
            throw PlayerNotInGameSessionException(this)

        val card = gameSession!!.takeCard(this, cardType)

        // TODO Выбор карты, которую положим в сброс
    }

    fun playToken(token: Token) {
        if (gameSession == null)
            throw PlayerNotInGameSessionException(this)

        gameSession!!.playToken(this, token)

        tokens.remove(token)
    }

    fun pass() {
        if (gameSession == null)
            throw PlayerNotInGameSessionException(this)

        gameSession!!.pass(this)
    }
}
