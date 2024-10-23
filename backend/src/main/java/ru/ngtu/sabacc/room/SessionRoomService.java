package ru.ngtu.sabacc.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.system.event.SessionRoomCreatedEvent;
import ru.ngtu.sabacc.system.event.SessionRoomDeletedEvent;
import ru.ngtu.sabacc.system.event.UserJoinedSessionRoomEvent;
import ru.ngtu.sabacc.system.exception.UserAlreadyJoinedSessionException;
import ru.ngtu.sabacc.system.exception.notfound.EntityNotFoundException;
import ru.ngtu.sabacc.user.User;
import ru.ngtu.sabacc.system.event.UserDeletedEvent;
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
        return sessionRoomRepository
                .findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(SessionRoom.class, sessionId))
                .getMembers();
    }

    public SessionRoom getRoomById(Long id) {
        return sessionRoomRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SessionRoom.class, id));
    }

    public List<SessionRoom> getAllUserSessionRooms(Long userId) {
        return sessionRoomRepository.findAllByMembers_Id(userId);
    }

    @Transactional
    public SessionRoom createSessionRoom(Long userId) {
        User user = userService.getUserById(userId);

        log.info("User [{}] creating session room", userId);

        List<User> members = new ArrayList<>(List.of(user));

        SessionRoom newSessionRoom = SessionRoom.builder()
                .members(members)
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
        if(sessionRoom.getMembers().contains(user))
            throw new UserAlreadyJoinedSessionException(sessionRoom.getId(), user.getId());

        sessionRoom.addMember(user);
        sessionRoomRepository.save(sessionRoom);
        eventPublisher.publishEvent(new UserJoinedSessionRoomEvent(roomId, userId));
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
}
