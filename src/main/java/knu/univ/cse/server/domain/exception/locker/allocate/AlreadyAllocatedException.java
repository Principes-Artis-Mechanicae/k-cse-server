package knu.univ.cse.server.domain.exception.locker.allocate;

import knu.univ.cse.server.global.exception.support.BadRequestException;

public class AlreadyAllocatedException extends BadRequestException {
	private static final String code = "ALREADY_ALLOCATED";

	public AlreadyAllocatedException() {
		super(code);
	}
}
