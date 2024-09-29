package ru.ngtu.sabacc.chat.room;

import lombok.Data;
import lombok.Value;

import java.util.List;

/**
 * DTO for {@link ChatRoom}
 */
@Data
public class CreateChatRoomDto {
    List<Long> memberIds;
}