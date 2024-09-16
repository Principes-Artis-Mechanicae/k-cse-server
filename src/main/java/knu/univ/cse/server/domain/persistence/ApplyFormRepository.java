package knu.univ.cse.server.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {
	ApplyForm findByYearAndSemester(Integer year, Integer semester);
}
