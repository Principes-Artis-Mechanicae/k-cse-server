package knu.univ.cse.server.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
