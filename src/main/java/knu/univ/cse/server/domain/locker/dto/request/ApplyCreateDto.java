package knu.univ.cse.server.domain.locker.dto.request;

import knu.univ.cse.server.domain.locker.entity.Apply;
import knu.univ.cse.server.domain.locker.entity.ApplyStatus;
import knu.univ.cse.server.domain.student.entity.Student;

public record ApplyCreateDto(
	String studentName, String studentNumber,
	String firstFloor, String firstHeight,
	String secondFloor, String secondHeight
) {
	public Apply toEntity(Student student) {
		return Apply.builder()
			.student(student)
			.firstFloor(firstFloor)
			.firstHeight(firstHeight)
			.secondFloor(secondFloor)
			.secondHeight(secondHeight)
			.status(ApplyStatus.APPLY)
			.build();
	}
}
