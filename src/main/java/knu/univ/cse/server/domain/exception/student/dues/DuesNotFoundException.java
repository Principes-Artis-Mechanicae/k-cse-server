package knu.univ.cse.server.domain.exception.student.dues;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class DuesNotFoundException extends NotFoundException {
	private static final String code = "DUES_NOT_FOUND";

	public DuesNotFoundException() {
		super(code);
	}
}
