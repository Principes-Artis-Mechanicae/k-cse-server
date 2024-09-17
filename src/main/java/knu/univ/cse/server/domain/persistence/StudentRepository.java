package knu.univ.cse.server.domain.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.student.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findStudentById(Long id);
	Optional<Student> findByStudentNameAndStudentNumber(String studentName, String studentNumber);
	Optional<Student> findByStudentNumber(String studentNumber);
}
