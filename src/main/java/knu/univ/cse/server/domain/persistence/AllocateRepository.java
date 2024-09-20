package knu.univ.cse.server.domain.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.allocate.Allocate;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;

public interface AllocateRepository extends JpaRepository<Allocate, Long> {
	boolean existsByStudentAndApplyForm(Student student, ApplyForm applyForm);
	void deleteByStudentAndApplyForm(Student student, ApplyForm applyForm);
	List<Allocate> findAllByApplyForm(ApplyForm applyForm);

	@EntityGraph(attributePaths = {"student", "locker", "apply", "applyForm"})
	Optional<Allocate> findByStudentAndApplyForm(Student student, ApplyForm applyForm);
}
