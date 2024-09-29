package ru.ngtu.sabacc.event;

import ru.ngtu.sabacc.chat.room.ChatRoom;

/**
 * @author Egor Bokov
 */
public record ChatRoomDeletedEvent(ChatRoom chatRoom) {
}
