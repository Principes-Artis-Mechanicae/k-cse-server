package knu.univ.cse.server.global.exception.support;

import org.springframework.http.HttpStatus;

import knu.univ.cse.server.global.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private final String errorCode;

	public CustomException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
