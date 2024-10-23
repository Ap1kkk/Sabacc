package ru.ngtu.sabacc.chat.message.mapper;

import ru.ngtu.sabacc.chat.message.ChatMessage;
import ru.ngtu.sabacc.chat.message.dto.UnsentChatMessageDto;
import ru.ngtu.sabacc.room.SessionRoom;

@org.mapstruct.Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring")
public interface UnsentChatMessageMapper {
    @org.mapstruct.Mapping(source = "chatRoomId", target = "sessionRoom")
    ChatMessage toEntity(UnsentChatMessageDto unsentChatMessageDto);

    @org.mapstruct.Mapping(source = "sessionRoom.id", target = "chatRoomId")
    UnsentChatMessageDto toDto(ChatMessage chatMessage);

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @org.mapstruct.Mapping(source = "chatRoomId", target = "sessionRoom")
    ChatMessage partialUpdate(UnsentChatMessageDto unsentChatMessageDto, @org.mapstruct.MappingTarget ChatMessage chatMessage);

    default SessionRoom createChatRoom(Long chatRoomId) {
        if (chatRoomId == null) {
            return null;
        }
        SessionRoom sessionRoom = new SessionRoom();
        sessionRoom.setId(chatRoomId);
        return sessionRoom;
    }
}