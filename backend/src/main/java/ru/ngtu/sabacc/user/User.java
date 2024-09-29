package ru.ngtu.sabacc.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.ngtu.sabacc.model.BaseEntity;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name = "expire_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_WITH_TIMEZONE)
    private Instant expireAt;
}