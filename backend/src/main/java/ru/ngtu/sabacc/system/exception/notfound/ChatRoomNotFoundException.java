package ru.ngtu.sabacc.system.exception.notfound;

import ru.ngtu.sabacc.room.SessionRoom;

/**
 * @author Egor Bokov
 */
public class ChatRoomNotFoundException extends EntityNotFoundException {
    public ChatRoomNotFoundException(Long chatRoomId) {
        super(SessionRoom.class, chatRoomId);
    }
}
