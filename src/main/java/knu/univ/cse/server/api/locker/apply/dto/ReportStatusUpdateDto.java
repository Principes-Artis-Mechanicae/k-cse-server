package knu.univ.cse.server.api.locker.apply.dto;

import jakarta.validation.constraints.NotNull;

public record ReportStatusUpdateDto(
	@NotNull Long reportId, @NotNull boolean isApproved
) {
}
