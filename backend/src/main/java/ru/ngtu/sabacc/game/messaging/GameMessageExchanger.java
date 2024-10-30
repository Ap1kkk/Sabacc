package ru.ngtu.sabacc.game.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ngtu.sabacc.gamecore.turn.TurnDTO;
import ru.ngtu.sabacc.game.GameErrorType;
import ru.ngtu.sabacc.ws.WebSocketMessageSender;

import static ru.ngtu.sabacc.constants.WebSocketApiEndpoint.WS_ACCEPTED_TURNS_QUEUE;
import static ru.ngtu.sabacc.constants.WebSocketApiEndpoint.WS_USER_SESSION_ERRORS_QUEUE;

/**
 * @author Egor Bokov
 */
@Component
@RequiredArgsConstructor
public class GameMessageExchanger implements IGameMessageExchanger {

    private final WebSocketMessageSender socketMessageSender;

    @Override
    public void sendErrorMessage(GameErrorType errorType, IGameSession sender) {
        socketMessageSender.sendMessageToSession(
                //TODO user id from error dto
                1L,
                sender.getSessionId(),
                WS_USER_SESSION_ERRORS_QUEUE,
                errorType
        );
    }

    @Override
    public void sendAcceptedTurn(TurnDTO turnDTO, IGameSession sender) {
        socketMessageSender.sendMessageSessionBroadcast(
                sender.getSessionId(),
                WS_ACCEPTED_TURNS_QUEUE,
                turnDTO
        );
    }
}
