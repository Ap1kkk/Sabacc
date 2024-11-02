package ru.ngtu.sabacc.game.messaging;

import ru.ngtu.sabacc.gamecore.game.GameErrorType;
import ru.ngtu.sabacc.gamecore.game.GameFinishDto;
import ru.ngtu.sabacc.gamecore.turn.TurnDto;

/**
 * @author Egor Bokov
 */
public interface IGameMessageExchanger {
    void sendErrorMessage(GameErrorType errorType, IGameSession sender);
    void sendAcceptedTurn(TurnDto turnDTO, IGameSession sender);
    void onGameFinished(GameFinishDto finishDto, IGameSession sender);
}
