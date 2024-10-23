package ru.ngtu.sabacc.chat.message;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.ngtu.sabacc.room.SessionRoom;
import ru.ngtu.sabacc.system.model.BaseEntity;

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
    @JoinColumn(name = "room_id", nullable = false)
    private SessionRoom sessionRoom;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "sent_at")
    @CreationTimestamp
    private Instant sentAt = Instant.now();
}