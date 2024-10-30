package ru.ngtu.sabacc.game.messaging;

import ru.ngtu.sabacc.game.GameStateDto;
import ru.ngtu.sabacc.gamecore.turn.TurnDTO;

/**
 * @author Egor Bokov
 */
public interface IGameSession {
    GameStateDto getCurrentState();
    void tryMakeTurn(TurnDTO turnDTO);
}
