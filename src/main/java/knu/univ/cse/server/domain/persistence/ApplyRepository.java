package knu.univ.cse.server.domain.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.locker.apply.Apply;
import knu.univ.cse.server.domain.model.student.Student;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
	Optional<Apply> findByStudent(Student student);
}
