package ru.ngtu.sabacc.system.exception.notfound;

import ru.ngtu.sabacc.chat.message.ChatMessage;

/**
 * @author Egor Bokov
 */
public class ChatMessageNotFoundException extends EntityNotFoundException {
    public ChatMessageNotFoundException(Long messageId) {
        super(ChatMessage.class, messageId);
    }
}