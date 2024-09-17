package knu.univ.cse.server.domain.exception.locker.apply;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class ApplyNotFoundException extends NotFoundException {
	private static final String code = "APPLY_NOT_FOUND";

	public ApplyNotFoundException() {
		super(code);
	}
}
