package ru.ngtu.sabacc.gamecore.game.session

import org.junit.jupiter.api.Test
import ru.ngtu.sabacc.gamecore.game.messaging.MockGameMessageExchanger
import ru.ngtu.sabacc.gamecore.token.Token
import ru.ngtu.sabacc.gamecore.turn.TurnDto
import ru.ngtu.sabacc.gamecore.turn.TurnType

class GameSessionTest {
    private val sessionId: Long = 0
    private val firstPlayerId: Long = 0
    private val secondPlayerId: Long = 1
    private val gameSession = GameSessionFactory.createSession(
        MockGameMessageExchanger(), sessionId
    )

    @Test
    fun testFullRound() {
        gameSession.start()

        println(gameSession.currentState)
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                firstPlayerId,
                TurnType.PLAY_TOKEN,
                Token.NO_TAX
            )
        )
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                firstPlayerId,
                TurnType.GET_SAND,
                null
            )
        )

        println(gameSession.currentState)
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                firstPlayerId,
                TurnType.DISCARD_SAND,
                1
            )
        )

        println(gameSession.currentState)
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                secondPlayerId,
                TurnType.GET_BLOOD,
                null
            )
        )

        println(gameSession.currentState)
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                secondPlayerId,
                TurnType.DISCARD_BLOOD,
                0
            )
        )

        println(gameSession.currentState)
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                firstPlayerId,
                TurnType.PASS,
                null
            )
        )

        println(gameSession.currentState)
        gameSession.tryMakeTurn(
            TurnDto(
                sessionId,
                secondPlayerId,
                TurnType.PASS,
                null
            )
        )
    }
}
