package knu.univ.cse.server.domain.locker.dto.response;

import knu.univ.cse.server.domain.locker.entity.ApplyForm;
import knu.univ.cse.server.global.util.DateTimeUtil;
import lombok.Builder;

@Builder
public record ApplyFormReadDto(
	Integer year, Integer semester,
	String firstApplyStartDate, String firstApplyEndDate,
	String semesterEndDate, String status
) {
	public static ApplyFormReadDto fromEntity(ApplyForm applyForm) {
		return ApplyFormReadDto.builder()
			.year(applyForm.getYear())
			.semester(applyForm.getSemester())
			.firstApplyStartDate(DateTimeUtil.localDateTimeToString(applyForm.getFirstApplyStartDate()))
			.firstApplyEndDate(DateTimeUtil.localDateTimeToString(applyForm.getFirstApplyEndDate()))
			.semesterEndDate(DateTimeUtil.localDateTimeToString(applyForm.getSemesterEndDate()))
			.status(applyForm.getStatus().name())
			.build();
	}
}
