package knu.univ.cse.server.global.exception.support;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class DuplicatedException extends ApplicationLogicException {
	private final HttpStatus httpStatus = HttpStatus.CONFLICT;
	public DuplicatedException(final String errorCode) {
		super(errorCode);
	}
}
