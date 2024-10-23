package ru.ngtu.sabacc.chat.message.mapper;

import org.mapstruct.*;
import ru.ngtu.sabacc.chat.message.ChatMessage;
import ru.ngtu.sabacc.chat.message.dto.SentChatMessageDto;
import ru.ngtu.sabacc.room.SessionRoom;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SentChatMessageMapper {
    @Mapping(source = "sessionRoomId", target = "sessionRoom")
    ChatMessage toEntity(SentChatMessageDto sentChatMessageDto);

    @Mapping(source = "sessionRoom.id", target = "sessionRoomId")
    SentChatMessageDto toDto(ChatMessage chatMessage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "sessionRoomId", target = "sessionRoom")
    ChatMessage partialUpdate(SentChatMessageDto sentChatMessageDto, @MappingTarget ChatMessage chatMessage);

    default SessionRoom createSessionRoom(Long sessionRoomId) {
        if (sessionRoomId == null) {
            return null;
        }
        SessionRoom sessionRoom = new SessionRoom();
        sessionRoom.setId(sessionRoomId);
        return sessionRoom;
    }
}