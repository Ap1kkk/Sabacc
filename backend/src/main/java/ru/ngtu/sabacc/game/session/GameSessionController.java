package ru.ngtu.sabacc.game.session;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.ngtu.sabacc.gamecore.turn.TurnDto;

import static ru.ngtu.sabacc.constants.WebSocketApiEndpoint.WS_SESSION_TURN_INPUT;

/**
 * @author Egor Bokov
 */
@Controller
@RequiredArgsConstructor
public class GameSessionController {

    private final GameSessionService gameSessionService;

    @MessageMapping(WS_SESSION_TURN_INPUT)
    //TODO remove sessionId from dto. Get it from request path
    public void logSessionTurnInput(TurnDto payload) {
        gameSessionService.makeTurn(payload);
    }
}
