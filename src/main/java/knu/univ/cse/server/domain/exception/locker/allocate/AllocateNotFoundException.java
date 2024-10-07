package knu.univ.cse.server.domain.exception.locker.allocate;

import knu.univ.cse.server.global.exception.support.NotFoundException;

public class AllocateNotFoundException extends NotFoundException {
	private static final String code = "ALLOCATE_NOT_FOUND";

	public AllocateNotFoundException() {
		super(code);
	}
}
