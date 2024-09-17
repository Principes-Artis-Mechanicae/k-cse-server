package knu.univ.cse.server.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import knu.univ.cse.server.global.exception.advice.ExceptionHandlerAdvice;
import knu.univ.cse.server.global.exception.support.ApplicationLogicException;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiErrorResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExceptionHandlerAdvice(order = 2)
public class ApplicationLogicExceptionHandler {

    @ExceptionHandler(ApplicationLogicException.class)
    public ResponseEntity<ApiErrorResult> handle(final ApplicationLogicException applicationLogicException) {
        log.debug(applicationLogicException.getMessage(), applicationLogicException);
        return ResponseEntity
            .status(applicationLogicException.getHttpStatus())
            .body(ApiUtil.error(
                applicationLogicException.getHttpStatus(),
                applicationLogicException.getErrorCode()
            ));
    }
}
