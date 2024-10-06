package ru.ngtu.sabacc.chat.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.event.ChatRoomDeletedEvent;
import ru.ngtu.sabacc.exception.notfound.ChatMessageNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessage getMessageById(Long id) {
        return chatMessageRepository.findById(id).orElse(null);
    }

    public List<ChatMessage> getAllMessagesByRoomId(Long chatRoomId) {
        return chatMessageRepository.findAllByChatRoom_IdOrderBySentAtAsc(chatRoomId);
    }

    public List<ChatMessageDto> getAllMessageDtosByRoomId(Long chatRoomId) {
        return getAllMessagesByRoomId(chatRoomId)
                .stream()
                .map(chatMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    public ChatMessage saveMessage(ChatMessageDto messageDto) {
        ChatMessage message = chatMessageMapper.toEntity(messageDto);
        message.setSentAt(Instant.now());
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

    @EventListener(ChatRoomDeletedEvent.class)
    @Transactional
    void onChatRoomDeleted(ChatRoomDeletedEvent event) {
        deleteAllMessagesFromChatRoom(event.chatRoom().getId());
    }
}
