package knu.univ.cse.server.domain.service.locker.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Report writeReport(String content, Apply apply) {
		return reportRepository.save(Report.builder()
			.content(content)
			.apply(apply)
			.build());
	}
}
