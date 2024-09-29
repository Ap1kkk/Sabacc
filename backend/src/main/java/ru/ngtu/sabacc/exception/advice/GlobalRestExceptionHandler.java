package ru.ngtu.sabacc.exception.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ngtu.sabacc.exception.AppException;
import ru.ngtu.sabacc.exception.message.ApplicationErrorMessage;

/**
 * @author Egor Bokov
 */
@ControllerAdvice
public class GlobalRestExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApplicationErrorMessage> handleException(Throwable ex) {
        return buildResponse(buildMessage(ex));
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationErrorMessage> handleException(AppException ex) {
        return buildResponse(buildMessage(ex));
    }
}
