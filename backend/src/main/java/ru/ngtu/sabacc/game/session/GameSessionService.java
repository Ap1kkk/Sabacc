package ru.ngtu.sabacc.game.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.ngtu.sabacc.game.messaging.IGameMessageExchanger;
import ru.ngtu.sabacc.game.messaging.IGameSession;
import ru.ngtu.sabacc.game.session.factory.IGameSessionFactory;
import ru.ngtu.sabacc.gamecore.game.GameStateDto;
import ru.ngtu.sabacc.gamecore.turn.TurnDto;
import ru.ngtu.sabacc.system.event.PlayerDisconnectedSessionEvent;
import ru.ngtu.sabacc.system.event.PlayerReconnectedSessionEvent;
import ru.ngtu.sabacc.system.event.SessionFinishedEvent;
import ru.ngtu.sabacc.system.event.SessionReadyEvent;
import ru.ngtu.sabacc.system.exception.session.GameSessionAlreadyExistsException;
import ru.ngtu.sabacc.system.exception.session.GameSessionNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Egor Bokov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GameSessionService {

    private final IGameSessionFactory sessionFactory;
    private final IGameMessageExchanger gameMessageExchanger;
    private final Map<Long, IGameSession> activeSessions = new ConcurrentHashMap<>();
//    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public GameStateDto getCurrentGameState(Long sessionId) {
        checkIfSessionExists(sessionId);
        return activeSessions.get(sessionId).getCurrentState();
    }

    public void makeTurn(TurnDto turnDTO) {
        long sessionId = turnDTO.getSessionId();
        checkIfSessionExists(sessionId);
        activeSessions.get(sessionId).tryMakeTurn(turnDTO);
    }

    @EventListener(SessionReadyEvent.class)
    void onSessionReady(SessionReadyEvent event) {
        Long sessionId = event.sessionId();
        log.info("Session [{}] is ready. Initializing game session...", sessionId);
        createSession(sessionId);
        startSession(sessionId);
    }

    @EventListener(PlayerDisconnectedSessionEvent.class)
    void onPlayerDisconnected(PlayerDisconnectedSessionEvent event) {
        Long sessionId = event.sessionId();
        Long playerId = event.playerId();
        log.info("Pausing session [{}] due to player [{}] disconnect", sessionId, playerId);
        pauseSession(sessionId);
    }

    @EventListener(PlayerReconnectedSessionEvent.class)
    void onPlayerReconnected(PlayerReconnectedSessionEvent event) {
        Long sessionId = event.sessionId();
        Long playerId = event.playerId();
        log.info("Continuing session [{}] due to player [{}] reconnect", sessionId, playerId);
        unpauseSession(sessionId);
    }

    @EventListener(SessionFinishedEvent.class)
    void onSessionFinished(SessionFinishedEvent event) {
        Long sessionId = event.sessionId();
        finishSession(sessionId);
    }

    private void pauseSession(Long sessionId) {
        checkIfSessionExists(sessionId);
        activeSessions.get(sessionId).pause();
        log.info("Game session [{}] paused.", sessionId);
    }

    private void unpauseSession(Long sessionId) {
        checkIfSessionExists(sessionId);
        activeSessions.get(sessionId).unpause();
        log.info("Game session [{}] continued.", sessionId);
    }

    private void startSession(Long sessionId) {
        checkIfSessionExists(sessionId);
        activeSessions.get(sessionId).start();
        log.info("Game session [{}] started.", sessionId);
    }

    private void createSession(Long sessionId) {
        if(activeSessions.containsKey(sessionId)) {
            throw new GameSessionAlreadyExistsException(sessionId);
        }
        IGameSession session = sessionFactory.createSession(gameMessageExchanger, sessionId);
        activeSessions.put(sessionId, session);
        log.info("Game session [{}] created.", sessionId);
    }

    private void finishSession(Long sessionId) {
        checkIfSessionExists(sessionId);
        activeSessions.remove(sessionId);
        log.info("Game session [{}] finished.", sessionId);
    }

    private void checkIfSessionExists(Long sessionId) {
        if(!activeSessions.containsKey(sessionId)) {
            throw new GameSessionNotFoundException(sessionId);
        }
    }
}
