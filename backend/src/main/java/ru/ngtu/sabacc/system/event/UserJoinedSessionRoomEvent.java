package ru.ngtu.sabacc.system.event;

/**
 * @author Egor Bokov
 */
public record UserJoinedSessionRoomEvent(Long roomId, Long userId) {
}
