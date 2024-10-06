package ru.ngtu.sabacc.event;

import ru.ngtu.sabacc.user.User;

/**
 * @author Egor Bokov
 */
public record UserDeletedEvent(User user) {
}
