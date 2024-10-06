package ru.ngtu.sabacc.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ngtu.sabacc.chat.message.ChatMessage;
import ru.ngtu.sabacc.chat.message.ChatMessageDto;
import ru.ngtu.sabacc.chat.room.ChatRoom;
import ru.ngtu.sabacc.chat.room.CreateChatRoomDto;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/rooms/all")
    @Transactional
    public List<ChatRoom> getAllChatRooms() {
        return chatService.getChatRooms();
    }

    @GetMapping("/room/{roomId}/history")
    @Transactional
    public List<ChatMessageDto> getChatRoomHistory(@PathVariable Long roomId) {
        return chatService.getChatRoomHistory(roomId);
    }

    @PostMapping("/new")
    @Transactional
    public ChatRoom startNewChat(@RequestBody CreateChatRoomDto dto) {
        return chatService.startNewChat(dto);
    }
}

