package ru.ngtu.sabacc.chat.message;

import ru.ngtu.sabacc.chat.room.ChatRoom;

@org.mapstruct.Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChatMessageMapper {
    @org.mapstruct.Mapping(source = "chatRoomId", target = "chatRoom")
    ChatMessage toEntity(ChatMessageDto chatMessageDto);

    @org.mapstruct.Mapping(source = "chatRoom.id", target = "chatRoomId")
    ChatMessageDto toDto(ChatMessage chatMessage);

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @org.mapstruct.Mapping(source = "chatRoomId", target = "chatRoom")
    ChatMessage partialUpdate(ChatMessageDto chatMessageDto, @org.mapstruct.MappingTarget ChatMessage chatMessage);

    default ChatRoom createChatRoom(Long chatRoomId) {
        if (chatRoomId == null) {
            return null;
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(chatRoomId);
        return chatRoom;
    }
}