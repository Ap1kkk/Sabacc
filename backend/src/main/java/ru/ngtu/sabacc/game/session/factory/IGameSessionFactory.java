package ru.ngtu.sabacc.game.session.factory;

import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger;
import ru.ngtu.sabacc.game.messaging.IGameSession;

/**
 * @author Egor Bokov
 */
public interface IGameSessionFactory {
    //TODO add required init parameters
    IGameSession createSession(IGameMessageExchanger messageExchanger, Long sessionId);
}
