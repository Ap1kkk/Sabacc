package ru.ngtu.sabacc.game;

import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger;
import ru.ngtu.sabacc.game.messaging.IGameSession;

/**
 * @author Egor Bokov
 */
public interface GameSessionFactory {
    //TODO add required init parameters
    IGameSession createSession(IGameMessageExchanger messageExchanger, Long sessionId);
}
