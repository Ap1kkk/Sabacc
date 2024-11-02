package ru.ngtu.sabacc.game.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Egor Bokov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameFinishDto {
    private Long sessionId;
}
