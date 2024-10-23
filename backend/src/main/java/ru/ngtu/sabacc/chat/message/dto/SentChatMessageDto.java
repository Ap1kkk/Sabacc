package ru.ngtu.sabacc.chat.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ngtu.sabacc.chat.message.ChatMessage;

import java.time.Instant;

/**
 * DTO for {@link ChatMessage}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentChatMessageDto {
    private Long id;
    private Long sessionRoomId;
    private String senderName;
    private String content;
    private Instant sentAt = Instant.now();
}