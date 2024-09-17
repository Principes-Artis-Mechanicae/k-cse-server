package knu.univ.cse.server.api.locker.applyForm.dto;

import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyFormStatus;
import knu.univ.cse.server.global.util.DateTimeUtil;
import lombok.Builder;

@Builder
public record ApplyFormReadDto(
	Integer year, Integer semester,
	String firstApplyStartDate, String firstApplyEndDate,
	String semesterEndDate, ApplyFormStatus status
) {
	public static ApplyFormReadDto fromEntity(ApplyForm applyForm) {
		return ApplyFormReadDto.builder()
			.year(applyForm.getYear())
			.semester(applyForm.getSemester())
			.firstApplyStartDate(DateTimeUtil.localDateTimeToString(applyForm.getFirstApplyStartDate()))
			.firstApplyEndDate(DateTimeUtil.localDateTimeToString(applyForm.getFirstApplyEndDate()))
			.semesterEndDate(DateTimeUtil.localDateTimeToString(applyForm.getSemesterEndDate()))
			.status(applyForm.getStatus())
			.build();
	}
}
