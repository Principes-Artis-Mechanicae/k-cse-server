package knu.univ.cse.server.global.exception.support;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ApplicationLogicException extends CustomException {
	private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private final String errorCode;

	public ApplicationLogicException(final String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
