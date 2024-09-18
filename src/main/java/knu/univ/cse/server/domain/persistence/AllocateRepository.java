package knu.univ.cse.server.domain.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.Locker;
import knu.univ.cse.server.domain.model.locker.allocate.Allocate;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;

public interface AllocateRepository extends JpaRepository<Allocate, Long> {
	boolean existsByApplyFormAndLocker(ApplyForm applyForm, Locker locker);
	List<Allocate> findAllByApplyForm(ApplyForm applyForm);
}
