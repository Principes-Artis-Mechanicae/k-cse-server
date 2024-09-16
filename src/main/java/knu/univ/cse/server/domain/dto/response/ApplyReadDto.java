package knu.univ.cse.server.domain.dto.response;

import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.Builder;

@Builder
public record ApplyReadDto(
	String studentName, String studentNumber,
	String firstFloor, String firstHeight,
	String secondFloor, String secondHeight,
	String status
) {
	public static ApplyReadDto fromEntity(Apply apply, Student student) {
		return ApplyReadDto.builder()
			.studentName(student.getStudentName())
			.studentNumber(student.getStudentNumber())
			.firstFloor(apply.getFirstFloor())
			.firstHeight(apply.getFirstHeight())
			.secondFloor(apply.getSecondFloor())
			.secondHeight(apply.getSecondHeight())
			.status(apply.getStatus().name())
			.build();
	}
}
