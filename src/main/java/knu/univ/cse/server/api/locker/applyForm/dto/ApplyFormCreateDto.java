package knu.univ.cse.server.api.locker.applyForm.dto;

import jakarta.validation.constraints.NotNull;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.global.util.DateTimeUtil;

public record ApplyFormCreateDto(
	@NotNull Integer year,
	@NotNull Integer semester,
	@NotNull String firstApplyStartDate,
	@NotNull String firstApplyEndDate,
	@NotNull String semesterEndDate
) {
	public ApplyForm toEntity() {
		return ApplyForm.builder()
			.year(year)
			.semester(semester)
			.firstApplyStartDate(DateTimeUtil.stringToLocalDateTime(firstApplyStartDate))
			.firstApplyEndDate(DateTimeUtil.stringToLocalDateTime(firstApplyEndDate))
			.semesterEndDate(DateTimeUtil.stringToLocalDateTime(semesterEndDate))
			.status(ApplyFormStatus.INACTIVE)
			.build();
	}
}
