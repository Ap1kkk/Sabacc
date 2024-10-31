package ru.ngtu.sabacc.gamecore.gamesession

import ru.ngtu.sabacc.game.GameSessionFactory
import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger
import ru.ngtu.sabacc.game.messaging.IGameSession

class GameSessionFactory : GameSessionFactory {
    override fun createSession(messageExchanger: IGameMessageExchanger, sessionId: Long): IGameSession {
        return GameSession(sessionId, messageExchanger)
    }
}
