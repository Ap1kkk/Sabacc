package ru.ngtu.sabacc.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Egor Bokov
 */
@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode {
    INTERNAL_ERROR("internal_error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("not_found", HttpStatus.NOT_FOUND),
    ALREADY_EXISTS("already_exists", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("validation_error", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus httpStatus;
}
