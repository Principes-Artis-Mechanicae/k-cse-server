package knu.univ.cse.server.domain.exception.locker.applyForm;

import knu.univ.cse.server.global.exception.support.DuplicatedException;

public class ApplyFormDuplicatedException extends DuplicatedException {
	private static final String code = "APPLY_FORM_DUPLICATED";

	public ApplyFormDuplicatedException() {
		super(code);
	}
}
