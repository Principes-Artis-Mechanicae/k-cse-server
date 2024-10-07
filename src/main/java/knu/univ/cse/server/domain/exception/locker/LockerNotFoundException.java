package knu.univ.cse.server.domain.exception.locker;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class LockerNotFoundException extends NotFoundException {
	private static final String code = "LOCKER_NOT_FOUND";

	public LockerNotFoundException() {
		super(code);
	}
}
