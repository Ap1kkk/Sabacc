CREATE TABLE chat_messages
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    room_id BIGINT                                       NOT NULL,
    sender_name  VARCHAR(255)                            NOT NULL,
    content      VARCHAR(255)                            NOT NULL,
    sent_at      TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    CONSTRAINT pk_chat_messages PRIMARY KEY (id)
);

CREATE TABLE session_rooms
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    CONSTRAINT pk_session_rooms PRIMARY KEY (id)
);

CREATE TABLE session_room_members
(
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username   VARCHAR(255)                            NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    expire_at  TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE chat_messages
    ADD CONSTRAINT fk_chat_messages_on_session_room FOREIGN KEY (room_id) REFERENCES session_rooms (id);

ALTER TABLE session_room_members
    ADD CONSTRAINT fk_session_room_members_on_session_room FOREIGN KEY (room_id) REFERENCES session_rooms (id);

ALTER TABLE session_room_members
    ADD CONSTRAINT fk_session_room_members_on_user FOREIGN KEY (user_id) REFERENCES users (id);