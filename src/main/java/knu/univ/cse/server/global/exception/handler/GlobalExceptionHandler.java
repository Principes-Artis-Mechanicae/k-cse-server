package knu.univ.cse.server.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import knu.univ.cse.server.global.exception.CustomException;
import knu.univ.cse.server.global.util.ApiUtil;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiUtil.ApiErrorResult> businessLogicException(final CustomException customException) {
        HttpStatus status = customException.getStatus();
        String message = customException.getMessage();

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiUtil.error(status, message));
    }
}
