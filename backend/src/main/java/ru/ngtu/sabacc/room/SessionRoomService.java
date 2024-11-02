package ru.ngtu.sabacc.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.system.event.*;
import ru.ngtu.sabacc.system.exception.notfound.EntityNotFoundException;
import ru.ngtu.sabacc.system.exception.session.*;
import ru.ngtu.sabacc.user.User;
import ru.ngtu.sabacc.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionRoomService {

    private final SessionRoomRepository sessionRoomRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public List<SessionRoom> getAllRooms() {
        return sessionRoomRepository.findAll();
    }

    @Transactional
    public List<User> getSessionMembers(Long sessionId) {
        SessionRoom sessionRoom = sessionRoomRepository
                .findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(SessionRoom.class, sessionId));

        return new ArrayList<>(List.of(sessionRoom.getPlayerFirst(), sessionRoom.getPlayerSecond()));
    }

    public SessionRoom getRoomById(Long id) {
        return sessionRoomRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SessionRoom.class, id));
    }

    public List<SessionRoom> getAllUserSessionRooms(Long userId) {
        return sessionRoomRepository.findAllByPlayerFirstIdOrPlayerSecondId(userId, userId);
    }

    @Transactional
    public SessionRoom createSessionRoom(Long userId) {
        //TODO fix: user can create only 1 session
        User user = userService.getUserById(userId);

        log.info("User [{}] creating session room", userId);
        sessionRoomRepository.findByPlayerFirstIdAndStatusNot(userId, SessionRoomStatus.FINISHED)
                .ifPresent(sessionRoom -> {throw new UnfinishedSessionException(userId);});

        SessionRoom newSessionRoom = SessionRoom.builder()
                .playerFirst(user)
                .status(SessionRoomStatus.WAITING_SECOND_USER)
                .build();

        SessionRoom createdSessionRoom = sessionRoomRepository.saveAndFlush(newSessionRoom);
        log.info("Session room: id={} was created", newSessionRoom.getId());

        eventPublisher.publishEvent(new SessionRoomCreatedEvent(createdSessionRoom));

        return createdSessionRoom;
    }

    @Transactional
    public void joinSession(Long roomId, Long userId) {
        log.info("User [{}] joining session [{}]", userId, roomId);

        SessionRoom sessionRoom = getRoomById(roomId);
        User user = userService.getUserById(userId);
        if(sessionRoom.getPlayerSecond() != null)
            throw new UserAlreadyJoinedSessionException(sessionRoom.getId(), user.getId());

        if(sessionRoom.getPlayerFirst().getId().equals(userId))
            throw new JoinSelfHostedSessionException(userId);

        sessionRoom.setPlayerSecond(user);
        sessionRoom.setStatus(SessionRoomStatus.ALL_USERS_JOINED);
        sessionRoomRepository.saveAndFlush(sessionRoom);
    }

    @Transactional
    public void deleteSessionRoom(SessionRoom sessionRoom) {
        log.info("Deleting session room: id={}", sessionRoom.getId());

        eventPublisher.publishEvent(new SessionRoomDeletedEvent(sessionRoom));
        sessionRoomRepository.delete(sessionRoom);
    }

    @EventListener(UserDeletedEvent.class)
    @Transactional
    void onUserDeleted(UserDeletedEvent event) {
        List<SessionRoom> allUserSessionRooms = getAllUserSessionRooms(event.user().getId());
        for (SessionRoom sessionRoom : allUserSessionRooms) {
            deleteSessionRoom(sessionRoom);
        }
        sessionRoomRepository.deleteAll(allUserSessionRooms);
    }

    @Transactional
    public void handlePlayerSocketConnection(Long sessionId, Long playerId) {
        SessionRoom sessionRoom = switchPlayerSocketConnected(sessionId, playerId, true);

        if(sessionRoom.isPlayerFirstConnected() && sessionRoom.isPlayerSecondConnected()) {
            if (sessionRoom.getStatus().equals(SessionRoomStatus.ALL_USERS_JOINED)) {
                sessionRoom.setStatus(SessionRoomStatus.ALL_USERS_CONNECTED);
                sessionRoomRepository.saveAndFlush(sessionRoom);
                eventPublisher.publishEvent(new SessionReadyEvent(sessionId, sessionRoom));
            }
            else {
                sessionRoom.setStatus(SessionRoomStatus.IN_PROGRESS);
                sessionRoomRepository.saveAndFlush(sessionRoom);
                eventPublisher.publishEvent(new PlayerReconnectedSessionEvent(sessionId, playerId, sessionRoom));
            }
        }
    }

    @Transactional
    public void handlePlayerSocketDisconnect(Long sessionId, Long playerId) {
        SessionRoom sessionRoom = switchPlayerSocketConnected(sessionId, playerId, false);

        if(!sessionRoom.isPlayerFirstConnected() && !sessionRoom.isPlayerSecondConnected()) {
            sessionRoom.setStatus(SessionRoomStatus.FINISHED);
            sessionRoomRepository.saveAndFlush(sessionRoom);
            eventPublisher.publishEvent(new SessionFinishedEvent(sessionId, sessionRoom));
            return;
        }

        sessionRoom.setStatus(SessionRoomStatus.PLAYER_DISCONNECTED);
        sessionRoomRepository.saveAndFlush(sessionRoom);
        eventPublisher.publishEvent(new PlayerDisconnectedSessionEvent(sessionId, playerId, sessionRoom));
    }

    @EventListener(SessionFinishedEvent.class)
    void onSessionFinished(SessionFinishedEvent event) {
        SessionRoom sessionRoom = sessionRoomRepository.findById(event.sessionId())
                .orElseThrow(() -> new EntityNotFoundException(SessionRoom.class, event.sessionId()));
        sessionRoom.setStatus(SessionRoomStatus.FINISHED);
        sessionRoomRepository.saveAndFlush(sessionRoom);
    }

    private SessionRoom switchPlayerSocketConnected(Long sessionId, Long playerId, boolean value) {
        SessionRoom sessionRoom = sessionRoomRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(SessionRoom.class, sessionId));

        User playerFirst = sessionRoom.getPlayerFirst();
        if(playerFirst.getId().equals(playerId)) {
            sessionRoom.setPlayerFirstConnected(value);
            sessionRoomRepository.saveAndFlush(sessionRoom);
            return sessionRoom;
        }

        User playerSecond = sessionRoom.getPlayerSecond();
        if(playerSecond == null) {
            throw new SecondPlayerIsNotJoinedException(sessionId);
        }

        if(playerSecond.getId().equals(playerId)) {
            sessionRoom.setPlayerSecondConnected(value);
            sessionRoomRepository.saveAndFlush(sessionRoom);
            return sessionRoom;
        }

        throw new PlayerNotRelatedToSessionException(sessionId, playerId);
    }
}
