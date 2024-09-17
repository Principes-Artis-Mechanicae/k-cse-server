package knu.univ.cse.server.domain.exception.locker.apply;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class InvalidApplyPeriodException extends NotFoundException {
	private static final String code = "APPLY_FORM_NOT_FOUND";

	public InvalidApplyPeriodException() {
		super(code);
	}
}
