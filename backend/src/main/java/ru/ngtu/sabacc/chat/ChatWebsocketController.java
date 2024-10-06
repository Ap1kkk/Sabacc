package ru.ngtu.sabacc.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.ngtu.sabacc.chat.message.ChatMessageDto;

/**
 * @author Egor Bokov
 */
@Controller
@RequiredArgsConstructor
public class ChatWebsocketController {

    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDto chatMessageDto) {
        chatService.sendMessage(chatMessageDto);
    }
}
