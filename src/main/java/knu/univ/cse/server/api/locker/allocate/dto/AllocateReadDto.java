package knu.univ.cse.server.api.locker.allocate.dto;

import knu.univ.cse.server.api.locker.apply.dto.ApplyReadDto;
import knu.univ.cse.server.api.locker.applyForm.dto.ApplyFormReadDto;
import knu.univ.cse.server.domain.model.locker.Locker;
import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;
import lombok.Builder;

@Builder
public record AllocateReadDto(
	String studentName, String studentNumber,
	String LockerName, LockerFloor floor, Integer height,
	ApplyReadDto apply, ApplyFormReadDto applyForm
) {
	public static AllocateReadDto fromEntity(Student student, Apply apply, ApplyForm applyForm, Locker locker) {
		return AllocateReadDto.builder()
			.studentName(student.getStudentName())
			.studentNumber(student.getStudentNumber())
			.LockerName(locker.getLockerName())
			.floor(locker.getFloor())
			.height(locker.getHeight())
			.apply(ApplyReadDto.fromEntity(apply, student))
			.applyForm(ApplyFormReadDto.fromEntity(applyForm))
			.build();
	}
}
