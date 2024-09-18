package knu.univ.cse.server.api.locker.apply.dto;

import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyHeight;
import knu.univ.cse.server.domain.model.locker.apply.ApplyPeriod;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.model.student.Student;

public record ApplyCreateDto(
	String studentName, String studentNumber,
	LockerFloor firstFloor, ApplyHeight firstHeight,
	LockerFloor secondFloor, ApplyHeight secondHeight
) {
	public Apply toEntity(Student student, ApplyPeriod period) {
		return Apply.builder()
			.student(student)
			.firstFloor(firstFloor)
			.firstHeight(firstHeight)
			.secondFloor(secondFloor)
			.secondHeight(secondHeight)
			.period(period)
			.status(ApplyStatus.APPLY)
			.build();
	}
}
