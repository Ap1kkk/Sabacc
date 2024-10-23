package ru.ngtu.sabacc.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.ngtu.sabacc.chat.message.dto.UnsentChatMessageDto;

/**
 * @author Egor Bokov
 */
@Controller
@RequiredArgsConstructor
public class ChatWebsocketController {

    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(UnsentChatMessageDto unsentChatMessageDto) {
        chatService.sendMessage(unsentChatMessageDto);
    }
}
