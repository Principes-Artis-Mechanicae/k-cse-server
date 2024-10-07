package knu.univ.cse.server.domain.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	Optional<Report> findByApply(Apply apply);
}
