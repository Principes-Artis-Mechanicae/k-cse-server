package knu.univ.cse.server.domain.exception.locker;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class LockerFullNotFoundException extends NotFoundException {
	private static final String code = "LOCKER_FULL_NOT_FOUND";

	public LockerFullNotFoundException() {
		super(code);
	}
}
