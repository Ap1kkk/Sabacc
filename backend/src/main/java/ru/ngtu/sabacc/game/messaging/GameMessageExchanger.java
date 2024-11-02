package ru.ngtu.sabacc.game.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.ngtu.sabacc.gamecore.game.GameFinishDto;
import ru.ngtu.sabacc.gamecore.turn.TurnDto;
import ru.ngtu.sabacc.gamecore.game.GameErrorType;
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
    public void sendAcceptedTurn(TurnDto turnDTO, IGameSession sender) {
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
