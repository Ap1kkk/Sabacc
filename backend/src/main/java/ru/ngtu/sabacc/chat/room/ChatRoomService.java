package ru.ngtu.sabacc.chat.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ngtu.sabacc.event.ChatRoomDeletedEvent;
import ru.ngtu.sabacc.user.User;
import ru.ngtu.sabacc.event.UserDeletedEvent;
import ru.ngtu.sabacc.user.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }

    public List<ChatRoom> getAllUserChatRooms(Long userId) {
        return chatRoomRepository.findAllByMembers_Id(userId);
    }

    @Transactional
    public ChatRoom createChatRoom(CreateChatRoomDto chatRoom) {
        log.info("Creating chat room: {}", chatRoom);

        List<User> members = userService.getAllByIds(chatRoom.getMemberIds());

        ChatRoom newChatRoom = ChatRoom.builder()
                .members(members)
                .build();

        return chatRoomRepository.save(newChatRoom);
    }

    @Transactional
    public void deleteChatRoom(ChatRoom chatRoom) {
        log.info("Deleting chat room: id={}", chatRoom.getId());

        eventPublisher.publishEvent(new ChatRoomDeletedEvent(chatRoom));
        chatRoomRepository.delete(chatRoom);
    }

    @EventListener(UserDeletedEvent.class)
    @Transactional
    void onUserDeleted(UserDeletedEvent event) {
        List<ChatRoom> allUserChatRooms = getAllUserChatRooms(event.user().getId());
        for (ChatRoom chatRoom : allUserChatRooms) {
            deleteChatRoom(chatRoom);
        }
        chatRoomRepository.deleteAll(allUserChatRooms);
    }
}
