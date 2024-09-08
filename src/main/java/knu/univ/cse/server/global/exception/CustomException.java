package knu.univ.cse.server.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomException extends RuntimeException {
	private final HttpStatus status;
	private final String message;

	public CustomException(HttpStatus status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}
}
