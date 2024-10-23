package ru.ngtu.sabacc.chat.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.chat.message.dto.SentChatMessageDto;
import ru.ngtu.sabacc.chat.message.dto.UnsentChatMessageDto;
import ru.ngtu.sabacc.chat.message.mapper.SentChatMessageMapper;
import ru.ngtu.sabacc.chat.message.mapper.UnsentChatMessageMapper;
import ru.ngtu.sabacc.system.event.SessionRoomDeletedEvent;
import ru.ngtu.sabacc.system.exception.notfound.ChatMessageNotFoundException;
import ru.ngtu.sabacc.user.User;
import ru.ngtu.sabacc.user.UserService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UnsentChatMessageMapper unsentChatMessageMapper;
    private final SentChatMessageMapper sentChatMessageMapper;
    private final UserService userService;

    public ChatMessage getMessageById(Long id) {
        return chatMessageRepository.findById(id).orElse(null);
    }

    public List<ChatMessage> getAllMessagesByRoomId(Long chatRoomId) {
        return chatMessageRepository.findAllBySessionRoom_IdOrderBySentAtAsc(chatRoomId);
    }

    public List<SentChatMessageDto> getAllMessageDtosByRoomId(Long chatRoomId) {
        return getAllMessagesByRoomId(chatRoomId)
                .stream()
                .map(sentChatMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    public ChatMessage saveMessage(UnsentChatMessageDto messageDto) {
        User user = userService.getUserById(messageDto.getUserId());
        ChatMessage message = unsentChatMessageMapper.toEntity(messageDto);
        message.setSentAt(Instant.now());
        message.setSenderName(user.getUsername());
        return chatMessageRepository.save(message);
    }

    public void deleteMessage(Long messageId) {
        ChatMessage message = getMessageById(messageId);
        if(message == null)
            throw new ChatMessageNotFoundException(messageId);

        chatMessageRepository.delete(message);
    }

    @Transactional
    public void deleteAllMessagesFromChatRoom(Long chatRoomId) {
        log.info("Deleting all messages from chat room: id={}", chatRoomId);
        List<ChatMessage> allChatMessages = getAllMessagesByRoomId(chatRoomId);
        chatMessageRepository.deleteAll(allChatMessages);
    }

    @EventListener(SessionRoomDeletedEvent.class)
    @Transactional
    void onChatRoomDeleted(SessionRoomDeletedEvent event) {
        deleteAllMessagesFromChatRoom(event.sessionRoom().getId());
    }
}
