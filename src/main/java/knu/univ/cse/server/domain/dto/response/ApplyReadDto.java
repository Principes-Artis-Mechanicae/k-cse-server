package knu.univ.cse.server.domain.dto.response;

import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.Builder;

@Builder
public record ApplyReadDto(
	String studentName, String studentNumber,
	LockerFloor firstFloor, Integer firstHeight,
	LockerFloor secondFloor, Integer secondHeight,
	ApplyStatus status
) {
	public static ApplyReadDto fromEntity(Apply apply, Student student) {
		return ApplyReadDto.builder()
			.studentName(student.getStudentName())
			.studentNumber(student.getStudentNumber())
			.firstFloor(apply.getFirstFloor())
			.firstHeight(apply.getFirstHeight())
			.secondFloor(apply.getSecondFloor())
			.secondHeight(apply.getSecondHeight())
			.status(apply.getStatus())
			.build();
	}
}
