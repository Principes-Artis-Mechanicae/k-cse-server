package knu.univ.cse.server.api.locker.apply.dto;

import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.apply.ApplyHeight;
import knu.univ.cse.server.domain.model.locker.apply.ApplyPeriod;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;

public record ApplyUpdateDto(
	String studentName, String studentNumber,
	LockerFloor firstFloor, ApplyHeight firstHeight,
	LockerFloor secondFloor, ApplyHeight secondHeight,
	ApplyPeriod period, ApplyStatus status
) {
}
