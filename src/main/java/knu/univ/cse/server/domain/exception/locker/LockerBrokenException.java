package knu.univ.cse.server.domain.exception.locker;

import knu.univ.cse.server.global.exception.support.BadRequestException;

public class LockerBrokenException extends BadRequestException {
	private static final String code = "LOCKER_BROKEN";

	public LockerBrokenException() {
		super(code);
	}
}
