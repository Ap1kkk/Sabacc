package ru.ngtu.sabacc.gamecore.game.session

import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger
import ru.ngtu.sabacc.game.messaging.IGameSession
import ru.ngtu.sabacc.game.session.factory.IGameSessionFactory

class GameSessionFactory : IGameSessionFactory {
    override fun createSession(messageExchanger: IGameMessageExchanger, sessionId: Long): IGameSession {
        return GameSession(sessionId, messageExchanger)
    }
}
