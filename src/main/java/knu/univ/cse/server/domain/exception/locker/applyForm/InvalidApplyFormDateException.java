package knu.univ.cse.server.domain.exception.locker.applyForm;

import knu.univ.cse.server.global.exception.support.BadRequestException;

public class InvalidApplyFormDateException extends BadRequestException {
	private static final String code = "INVALID_APPLY_FORM_DATE";

	public InvalidApplyFormDateException() {
		super(code);
	}
}
