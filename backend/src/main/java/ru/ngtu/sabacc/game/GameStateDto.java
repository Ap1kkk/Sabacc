package ru.ngtu.sabacc.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Egor Bokov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStateDto {
    private Long playerId;
    //TODO add required data for reproduce game state if user reconnected
}
