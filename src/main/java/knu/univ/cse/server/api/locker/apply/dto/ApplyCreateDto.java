package knu.univ.cse.server.api.locker.apply.dto;

import jakarta.validation.constraints.NotNull;
import knu.univ.cse.server.domain.model.locker.LockerFloor;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyHeight;
import knu.univ.cse.server.domain.model.locker.apply.ApplyPeriod;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;

public record ApplyCreateDto(
	@NotNull String studentName, @NotNull String studentNumber,
	@NotNull LockerFloor firstFloor, @NotNull ApplyHeight firstHeight,
	@NotNull LockerFloor secondFloor, @NotNull ApplyHeight secondHeight
) {
	public Apply toEntity(Student student, ApplyPeriod period, ApplyForm applyForm) {
		return Apply.builder()
			.student(student)
			.applyForm(applyForm)
			.firstFloor(firstFloor)
			.firstHeight(firstHeight)
			.secondFloor(secondFloor)
			.secondHeight(secondHeight)
			.period(period)
			.status(
				period == ApplyPeriod.REPLACEMENT ? ApplyStatus.BROKEN_APPLY : ApplyStatus.APPLY
			).build();
	}
}
