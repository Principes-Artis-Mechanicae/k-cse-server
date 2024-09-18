package knu.univ.cse.server.domain.exception.locker.apply;

import knu.univ.cse.server.global.exception.support.BadRequestException;

public class InvalidApplyPeriodException extends BadRequestException {
	private static final String code = "INVALID_APPLY_PERIOD";

	public InvalidApplyPeriodException() {
		super(code);
	}
}
