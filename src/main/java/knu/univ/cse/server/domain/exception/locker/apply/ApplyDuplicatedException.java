package knu.univ.cse.server.domain.exception.locker.apply;

import knu.univ.cse.server.global.exception.support.DuplicatedException;

public class ApplyDuplicatedException extends DuplicatedException {
	private static final String code = "APPLY_DUPLICATED";

	public ApplyDuplicatedException() {
		super(code);
	}
}
