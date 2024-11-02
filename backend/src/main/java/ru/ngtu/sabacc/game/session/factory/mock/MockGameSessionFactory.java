package ru.ngtu.sabacc.game.session.factory.mock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger;
import ru.ngtu.sabacc.game.messaging.IGameSession;
import ru.ngtu.sabacc.game.session.factory.IGameSessionFactory;

/**
 * @author Egor Bokov
 */
@Component
//@ConditionalOnMissingBean(IGameSessionFactory.class)
public class MockGameSessionFactory implements IGameSessionFactory {

    @Override
    public IGameSession createSession(IGameMessageExchanger messageExchanger, Long sessionId) {
        return new MockGameSession(sessionId);
    }
}
