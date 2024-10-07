package knu.univ.cse.server.global.exception.support;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BadRequestException extends ApplicationLogicException {
	private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	public BadRequestException(final String errorCode) {
		super(errorCode);
	}
}
