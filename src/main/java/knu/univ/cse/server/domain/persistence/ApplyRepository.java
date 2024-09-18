package knu.univ.cse.server.domain.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.locker.apply.ApplyStatus;
import knu.univ.cse.server.domain.model.locker.applyForm.ApplyForm;
import knu.univ.cse.server.domain.model.student.Student;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
	Optional<Apply> findByStudent(Student student);
	Optional<Apply> findByStudentAndApplyFormAndStatus(Student student, ApplyForm applyForm, ApplyStatus status);
	@EntityGraph(attributePaths = {"student"})
	List<Apply> findAllByApplyFormAndStatus(ApplyForm applyForm, ApplyStatus status);
	long countByStudentAndApplyFormAndStatus(Student student, ApplyForm applyForm, ApplyStatus status);

	@EntityGraph(attributePaths = {"student"})
	List<Apply> findAllByApplyForm(ApplyForm applyForm);

	Optional<Apply> findByStudentAndApplyForm(Student student, ApplyForm applyForm);
}
