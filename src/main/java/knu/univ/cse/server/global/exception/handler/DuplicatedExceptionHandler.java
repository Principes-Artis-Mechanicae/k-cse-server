package knu.univ.cse.server.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import knu.univ.cse.server.global.exception.advice.ExceptionHandlerAdvice;
import knu.univ.cse.server.global.exception.support.DuplicatedException;
import knu.univ.cse.server.global.exception.support.NotFoundException;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiErrorResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExceptionHandlerAdvice(order = 1)
public class DuplicatedExceptionHandler {

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<ApiErrorResult> handle(final DuplicatedException duplicatedException) {
        log.debug(duplicatedException.getMessage(), duplicatedException);
        return ResponseEntity
            .status(duplicatedException.getHttpStatus())
            .body(ApiUtil.error(
                duplicatedException.getHttpStatus(),
                duplicatedException.getErrorCode()
            ));
    }
}
