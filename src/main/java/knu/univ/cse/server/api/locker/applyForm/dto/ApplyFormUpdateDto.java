package knu.univ.cse.server.api.locker.applyForm.dto;

import lombok.Builder;

@Builder
public record ApplyFormUpdateDto(
	String firstApplyStartDate, String firstApplyEndDate,
	String semesterEndDate, String status
) {
}
