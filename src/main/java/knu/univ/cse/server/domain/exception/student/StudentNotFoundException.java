package knu.univ.cse.server.domain.exception.student;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class StudentNotFoundException extends NotFoundException {
	private static final String code = "STUDENT_NOT_FOUND";

	public StudentNotFoundException() {
		super(code);
	}
}
