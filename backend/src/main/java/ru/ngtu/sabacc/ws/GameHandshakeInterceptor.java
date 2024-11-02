package ru.ngtu.sabacc.ws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ngtu.sabacc.room.SessionRoomService;
import ru.ngtu.sabacc.system.exception.AppException;

import java.util.Map;

/**
 * @author Egor Bokov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GameHandshakeInterceptor implements HandshakeInterceptor {

    private final SessionRoomService sessionService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Извлекаем параметры из URL (или заголовков запроса)
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(request.getURI());
        String sessionId = uriBuilder.build().getQueryParams().getFirst("sessionId");
        String playerId = uriBuilder.build().getQueryParams().getFirst("playerId");

        if (sessionId != null && playerId != null) {
            attributes.put("sessionId", Long.valueOf(sessionId));
            attributes.put("playerId", Long.valueOf(playerId));
            log.info("WS: Player [{}] connecting to session [{}]", playerId, sessionId);
            try {
                sessionService.handlePlayerSocketConnection(Long.valueOf(sessionId), Long.valueOf(playerId));
            } catch (AppException e) {
                log.error(e.getMessage());
                response.setStatusCode(e.getErrorCode().getHttpStatus());
                return false;
            }
            log.info("WS: Player [{}] connected to session [{}]", playerId, sessionId);
            return true;
        } else {
            log.error("WS: handshake declined. Parameters sessionId and playerId must not be null!");
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
