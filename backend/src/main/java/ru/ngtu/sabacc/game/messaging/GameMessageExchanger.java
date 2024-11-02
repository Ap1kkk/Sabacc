package ru.ngtu.sabacc.game.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.ngtu.sabacc.game.session.GameFinishDto;
import ru.ngtu.sabacc.gamecore.turn.TurnDTO;
import ru.ngtu.sabacc.game.GameErrorType;
import ru.ngtu.sabacc.ws.WebSocketMessageSender;

import static ru.ngtu.sabacc.constants.WebSocketApiEndpoint.*;

/**
 * @author Egor Bokov
 */
@Component
@RequiredArgsConstructor
public class GameMessageExchanger implements IGameMessageExchanger {

    private final WebSocketMessageSender socketMessageSender;
    private final ApplicationEventPublisher eventPublisher;

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

    @Override
    public void onGameFinished(GameFinishDto finishDto, IGameSession sender) {
        socketMessageSender.sendMessageSessionBroadcast(
                sender.getSessionId(),
                WS_GAME_RESULTS_TOPIC,
                finishDto
        );

    }
}
