package knu.univ.cse.server.api.locker.apply.dto;

public record ApplyReportCreateDto(
	ApplyCreateDto apply, String content
) {
}
