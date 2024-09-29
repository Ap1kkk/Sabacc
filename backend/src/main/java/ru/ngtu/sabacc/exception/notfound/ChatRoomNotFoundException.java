package ru.ngtu.sabacc.exception.notfound;

import ru.ngtu.sabacc.chat.room.ChatRoom;

/**
 * @author Egor Bokov
 */
public class ChatRoomNotFoundException extends EntityNotFoundException {
    public ChatRoomNotFoundException(Long chatRoomId) {
        super(ChatRoom.class, chatRoomId);
    }
}
