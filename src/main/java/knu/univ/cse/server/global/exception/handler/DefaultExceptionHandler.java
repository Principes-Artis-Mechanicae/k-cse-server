package knu.univ.cse.server.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import knu.univ.cse.server.global.exception.advice.ExceptionHandlerAdvice;
import knu.univ.cse.server.global.util.ApiUtil;
import knu.univ.cse.server.global.util.ApiUtil.ApiErrorResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExceptionHandlerAdvice(order = 4)
public class DefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResult> handle(final Exception exception) {
		log.error(exception.getMessage(), exception);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiUtil.error(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"INTERNAL_SERVER_ERROR"
			));
	}
}
