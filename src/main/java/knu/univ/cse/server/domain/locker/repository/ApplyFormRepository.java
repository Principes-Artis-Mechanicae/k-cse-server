package knu.univ.cse.server.domain.locker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.locker.entity.Apply;
import knu.univ.cse.server.domain.locker.entity.ApplyForm;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {
	ApplyForm findByYearAndSemester(Integer year, Integer semester);
}
