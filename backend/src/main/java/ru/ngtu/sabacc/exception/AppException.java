package ru.ngtu.sabacc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.ngtu.sabacc.exception.message.ApplicationErrorCode;

/**
 * @author Egor Bokov
 */
@Getter
public class AppException extends RuntimeException {
    private static final ApplicationErrorCode DEFAULT_ERROR_CODE = ApplicationErrorCode.INTERNAL_ERROR;

    private final String message;
    private final ApplicationErrorCode errorCode;

    public AppException(String message) {
        super(message);
        this.message = message;
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    public AppException(String message, ApplicationErrorCode errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
