package knu.univ.cse.server.api.student.dto;

import knu.univ.cse.server.domain.model.student.Role;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.Builder;

@Builder
public record StudentReadDto(
	Long studentId, String studentName,
	String studentNumber, Role role,
	boolean dues
) {
	public static StudentReadDto fromEntity(Student student) {
		return StudentReadDto.builder()
			.studentId(student.getId())
			.studentName(student.getStudentName())
			.studentNumber(student.getStudentNumber())
			.role(student.getRole())
			.dues(student.getDues().isDues())
			.build();
	}
}
