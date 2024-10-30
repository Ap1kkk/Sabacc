package ru.ngtu.sabacc.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.chat.message.ChatMessage;
import ru.ngtu.sabacc.chat.message.dto.SentChatMessageDto;
import ru.ngtu.sabacc.chat.message.mapper.SentChatMessageMapper;
import ru.ngtu.sabacc.chat.message.mapper.UnsentChatMessageMapper;
import ru.ngtu.sabacc.chat.message.dto.UnsentChatMessageDto;
import ru.ngtu.sabacc.chat.message.ChatMessageService;
import ru.ngtu.sabacc.room.SessionRoom;
import ru.ngtu.sabacc.room.SessionRoomService;
import ru.ngtu.sabacc.system.event.UserJoinedSessionRoomEvent;
import ru.ngtu.sabacc.system.exception.notfound.ChatRoomNotFoundException;
import ru.ngtu.sabacc.user.UserService;
import ru.ngtu.sabacc.ws.WebSocketMessageSender;

import java.util.List;

import static ru.ngtu.sabacc.constants.WebSocketApiEndpoint.WS_SESSION_CHAT_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final WebSocketMessageSender socketMessageSender;
    private final ChatMessageService chatMessageService;
    private final UnsentChatMessageMapper unsentChatMessageMapper;
    private final SentChatMessageMapper sentChatMessageMapper;
    private final SessionRoomService sessionRoomService;
    private final UserService userService;

    public List<SentChatMessageDto> getChatRoomHistory(Long chatRoomId) {
        return chatMessageService.getAllMessageDtosByRoomId(chatRoomId);
    }

    @Transactional
    public void sendMessage(UnsentChatMessageDto unsentChatMessageDto) {
        log.info("Sending chat message {}", unsentChatMessageDto);

        Long chatRoomId = unsentChatMessageDto.getChatRoomId();
        SessionRoom sessionRoom = sessionRoomService.getRoomById(chatRoomId);
        if(sessionRoom == null) {
            throw new ChatRoomNotFoundException(chatRoomId);
        }

        ChatMessage savedMessage = chatMessageService.saveMessage(unsentChatMessageDto);
        SentChatMessageDto savedMessageDto = sentChatMessageMapper.toDto(savedMessage);

        socketMessageSender.sendMessageBroadcast(WS_SESSION_CHAT_TOPIC.formatted(chatRoomId), savedMessageDto);
    }

    @EventListener(UserJoinedSessionRoomEvent.class)
    void onUserJoinedSessionRoomEvent(UserJoinedSessionRoomEvent event) {
        userService.getUserById(event.userId());
    }
}
