package knu.univ.cse.server.api.locker.apply.dto;

import knu.univ.cse.server.domain.model.locker.report.Report;
import lombok.Builder;

@Builder
public record ApplyReportReadDto(
	Long reportId, ApplyReadDto apply, String content
) {
	public static ApplyReportReadDto of(ApplyReadDto apply, Report report) {
		return ApplyReportReadDto.builder()
			.reportId(report.getId())
			.apply(apply)
			.content(report.getContent())
			.build();
	}
}
