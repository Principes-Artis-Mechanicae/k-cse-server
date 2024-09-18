package knu.univ.cse.server.domain.exception.locker.allocate;

import knu.univ.cse.server.global.exception.support.DuplicatedException;

public class AllocateDuplicatedException extends DuplicatedException {
	private static final String code = "ALLOCATE_DUPLICATED";

	public AllocateDuplicatedException() {
		super(code);
	}
}
