package knu.univ.cse.server.api.locker.apply.dto;

import jakarta.validation.constraints.NotNull;

public record ApplyReportCreateDto(
	@NotNull ApplyCreateDto apply, @NotNull String content
) {
}
