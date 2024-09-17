package knu.univ.cse.server.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import knu.univ.cse.server.global.exception.advice.ExceptionHandlerAdvice;
import knu.univ.cse.server.global.exception.support.BadRequestException;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiErrorResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExceptionHandlerAdvice(order = 1)
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResult> handle(final BadRequestException badRequestException) {
        log.debug(badRequestException.getMessage(), badRequestException);
        return ResponseEntity
            .status(badRequestException.getHttpStatus())
            .body(ApiUtil.error(
                badRequestException.getHttpStatus(),
                badRequestException.getErrorCode()
            ));
    }
}
