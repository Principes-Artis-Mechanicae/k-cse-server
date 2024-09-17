package knu.univ.cse.server.domain.exception.locker;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class ApplyFormNotFoundException extends NotFoundException {
	private static final String code = "APPLY_FORM_NOT_FOUND";

	public ApplyFormNotFoundException() {
		super(code);
	}
}
