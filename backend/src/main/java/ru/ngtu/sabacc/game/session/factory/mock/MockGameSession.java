package ru.ngtu.sabacc.game.session.factory.mock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.ngtu.sabacc.game.GameStateDto;
import ru.ngtu.sabacc.game.messaging.IGameSession;
import ru.ngtu.sabacc.gamecore.turn.TurnDTO;

/**
 * @author Egor Bokov
 */
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class MockGameSession implements IGameSession {

    private final Long sessionId;

    @Override
    public GameStateDto getCurrentState() {
        return new GameStateDto();
    }

    @Override
    public void tryMakeTurn(TurnDTO turnDTO) {
        log.debug("Mock game session [{}] trying to make turn", sessionId);
    }

    @Override
    public void start() {
        log.debug("Mock game session [{}] started", sessionId);
    }

    @Override
    public void pause() {
        log.debug("Mock game session [{}] paused", sessionId);
    }

    @Override
    public void unpause() {
        log.debug("Mock game session [{}] un paused", sessionId);
    }
}
