package knu.univ.cse.server.global.exception;

import org.springframework.http.HttpStatus;

public class CustomAssert {
	public static <T> void notFound(T entity, Class<T> clazz) {
		if (entity == null) {
			throw CustomException.builder()
				.status(HttpStatus.NOT_FOUND)
				.message("Not Found " + clazz.getSimpleName())
				.build();
		}
	}
}
