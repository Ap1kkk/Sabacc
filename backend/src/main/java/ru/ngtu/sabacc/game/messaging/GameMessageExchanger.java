package ru.ngtu.sabacc.game.messaging;

import org.springframework.stereotype.Component;
import ru.ngtu.sabacc.gamecore.turn.TurnDTO;
import ru.ngtu.sabacc.game.GameErrorType;
import ru.ngtu.sabacc.game.GameStateDto;

/**
 * @author Egor Bokov
 */
@Component
public class GameMessageExchanger implements IGameMessageExchanger {


    @Override
    public void sendErrorMessage(GameErrorType errorType) {

    }

    @Override
    public void sendAcceptedTurn(TurnDTO turnDTO) {

    }
}
