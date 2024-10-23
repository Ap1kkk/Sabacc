package ru.ngtu.sabacc.room;

import jakarta.persistence.*;
import lombok.*;
import ru.ngtu.sabacc.system.model.BaseEntity;
import ru.ngtu.sabacc.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session_rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRoom extends BaseEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "session_room_members",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private List<User> members = new ArrayList<>();


    public void addMember(User user) {
        if(members == null) {
            members = new ArrayList<>(List.of(user));
            return;
        }

        members.add(user);
    }
}