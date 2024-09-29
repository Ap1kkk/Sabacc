package ru.ngtu.sabacc.chat.message;

import lombok.Data;
import lombok.Value;

import java.time.Instant;

/**
 * DTO for {@link ChatMessage}
 */
@Data
public class ChatMessageDto {
    Long id;
    Long chatRoomId;
    String senderName;
    String content;
    Instant sentAt;
}