package knu.univ.cse.server.domain.locker.dto.request;

import lombok.Builder;

@Builder
public record ApplyFormUpdateDto(
	String firstApplyStartDate, String firstApplyEndDate,
	String semesterEndDate, String status
) {
}
