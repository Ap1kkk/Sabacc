package ru.ngtu.sabacc.chat.message.dto;

import lombok.Data;
import ru.ngtu.sabacc.chat.message.ChatMessage;

/**
 * DTO for {@link ChatMessage}
 */
@Data
public class UnsentChatMessageDto {
    Long userId;
    Long chatRoomId;
    String content;
}