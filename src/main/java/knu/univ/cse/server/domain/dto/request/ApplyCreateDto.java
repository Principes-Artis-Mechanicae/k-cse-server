package knu.univ.cse.server.domain.dto.request;

import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.model.student.Student;

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
