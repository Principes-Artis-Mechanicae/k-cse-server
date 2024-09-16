package knu.univ.cse.server.domain.dto.request;

import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.global.util.DateTimeUtil;

public record ApplyFormCreateDto(
	Integer year, Integer semester,
	String firstApplyStartDate, String firstApplyEndDate,
	String semesterEndDate
) {
	public ApplyForm toEntity() {
		return ApplyForm.builder()
			.year(year)
			.semester(semester)
			.firstApplyStartDate(DateTimeUtil.stringToLocalDateTime(firstApplyEndDate))
			.firstApplyEndDate(DateTimeUtil.stringToLocalDateTime(firstApplyStartDate))
			.semesterEndDate(DateTimeUtil.stringToLocalDateTime(semesterEndDate))
			.status(ApplyFormStatus.INACTIVE)
			.build();
	}
}
