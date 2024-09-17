package knu.univ.cse.server.api.locker.apply.dto;

import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.apply.ApplyPeriod;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;

public record ApplyUpdateDto(
	String studentName, String studentNumber,
	LockerFloor firstFloor, Integer firstHeight,
	LockerFloor secondFloor, Integer secondHeight,
	ApplyPeriod period, ApplyStatus status
) {
}
