package ru.ngtu.sabacc.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Egor Bokov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameErrorDto {
    private Long playerId;
    private Long sessionId;
    private GameErrorType errorType;
    private Map<String, Object> details;
}
