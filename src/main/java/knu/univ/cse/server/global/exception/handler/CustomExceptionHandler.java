package knu.univ.cse.server.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import knu.univ.cse.server.global.exception.support.CustomException;
import knu.univ.cse.server.global.exception.advice.ExceptionHandlerAdvice;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiErrorResult;
import lombok.extern.log4j.Log4j2;


@Log4j2
@ExceptionHandlerAdvice(order = 3)
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResult> handle(final CustomException customException) {
        log.debug(customException.getMessage(), customException);
        return ResponseEntity
            .status(customException.getHttpStatus())
            .body(ApiUtil.error(
                customException.getHttpStatus(),
                customException.getErrorCode()
            ));
    }
}
