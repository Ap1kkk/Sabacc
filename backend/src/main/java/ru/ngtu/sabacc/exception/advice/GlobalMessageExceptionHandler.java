package ru.ngtu.sabacc.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ngtu.sabacc.exception.AppException;
import ru.ngtu.sabacc.exception.message.ApplicationErrorMessage;

/**
 * @author Egor Bokov
 */
@ControllerAdvice
public class GlobalMessageExceptionHandler extends BaseExceptionHandler {

    @MessageExceptionHandler
    public ResponseEntity<ApplicationErrorMessage> handleException(Throwable ex) {
        return buildResponse(buildMessage(ex));
    }

    @MessageExceptionHandler
    public ResponseEntity<ApplicationErrorMessage> handleException(AppException ex) {
        return buildResponse(buildMessage(ex));
    }
}
