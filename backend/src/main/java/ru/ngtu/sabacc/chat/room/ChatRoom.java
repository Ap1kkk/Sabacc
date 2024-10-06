package ru.ngtu.sabacc.chat.room;

import jakarta.persistence.*;
import lombok.*;
import ru.ngtu.sabacc.model.BaseEntity;
import ru.ngtu.sabacc.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chat_rooms_members",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private List<User> members = new ArrayList<>();

}