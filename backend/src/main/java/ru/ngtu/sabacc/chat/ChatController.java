package ru.ngtu.sabacc.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ngtu.sabacc.chat.message.dto.SentChatMessageDto;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/room/{roomId}/history")
    @Transactional
    public List<SentChatMessageDto> getChatRoomHistory(@PathVariable Long roomId) {
        return chatService.getChatRoomHistory(roomId);
    }
}
