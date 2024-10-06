package ru.ngtu.sabacc.chat.message;

import jakarta.persistence.*;
import lombok.*;
import ru.ngtu.sabacc.chat.room.ChatRoom;
import ru.ngtu.sabacc.model.BaseEntity;

import java.time.Instant;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "sent_at")
    private Instant sentAt = Instant.now();

}