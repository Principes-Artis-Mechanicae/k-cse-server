package knu.univ.cse.server.domain.service.locker.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import knu.univ.cse.server.domain.exception.locker.report.ReportNotFoundException;
import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.report.Report;
import knu.univ.cse.server.domain.persistence.ReportRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {
	/* internal dependency */
	private final ReportRepository reportRepository;

	/**
	 * 보고서를 작성하여 저장합니다.
	 *
	 * @param content 보고서 내용
	 * @param apply 신청 엔티티
	 * @return 저장된 보고서 엔티티
	 */
	@Transactional
	public Report writeReport(String content, Apply apply) {
		return reportRepository.save(Report.builder()
			.content(content)
			.apply(apply)
			.build());
	}


	/**
	 * 모든 보고서를 조회합니다.
	 *
	 * @throws ReportNotFoundException "REPORT_NOT_FOUND"
	 * @return 모든 보고서의 DTO 리스트
	 */
	public Report getReportByApply(Apply apply) {
		return reportRepository.findByApply(apply)
			.orElseThrow(ReportNotFoundException::new);
	}
}
