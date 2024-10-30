package ru.ngtu.sabacc.game.messaging;

import ru.ngtu.sabacc.game.GameErrorType;
import ru.ngtu.sabacc.gamecore.turn.TurnDTO;

/**
 * @author Egor Bokov
 */
public interface IGameMessageExchanger {
    void sendErrorMessage(GameErrorType errorType);
    void sendAcceptedTurn(TurnDTO turnDTO);
}
