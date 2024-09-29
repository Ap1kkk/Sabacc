package ru.ngtu.sabacc.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.chat.message.ChatMessage;
import ru.ngtu.sabacc.chat.message.ChatMessageDto;
import ru.ngtu.sabacc.chat.message.ChatMessageMapper;
import ru.ngtu.sabacc.chat.message.ChatMessageService;
import ru.ngtu.sabacc.chat.room.ChatRoom;
import ru.ngtu.sabacc.chat.room.ChatRoomService;
import ru.ngtu.sabacc.chat.room.CreateChatRoomDto;
import ru.ngtu.sabacc.exception.notfound.ChatRoomNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private static final String DESTINATION_TEMPLATE = "/topic/chat/%d";

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatRoomService chatRoomService;

    public List<ChatMessageDto> getChatRoomHistory(Long chatRoomId) {
        return chatMessageService.getAllMessageDtosByRoomId(chatRoomId);
    }

    @Transactional
    public List<ChatRoom> getChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @Transactional
    public ChatRoom startNewChat(CreateChatRoomDto dto) {
        return chatRoomService.createChatRoom(dto);
    }

    @Transactional
    public void sendMessage(ChatMessageDto chatMessageDto) {
        log.info("Sending chat message {}", chatMessageDto);

        Long chatRoomId = chatMessageDto.getChatRoomId();
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        if(chatRoom == null) {
            throw new ChatRoomNotFoundException(chatRoomId);
        }

        ChatMessage savedMessage = chatMessageService.saveMessage(chatMessageDto);
        ChatMessageDto savedMessageDto = chatMessageMapper.toDto(savedMessage);

        String destination = DESTINATION_TEMPLATE.formatted(chatRoomId);
        messagingTemplate.convertAndSend(destination, savedMessageDto);
    }
}
