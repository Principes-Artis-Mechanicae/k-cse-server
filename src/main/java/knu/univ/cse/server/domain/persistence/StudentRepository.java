package knu.univ.cse.server.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.student.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findStudentById(Long id);
	Student findByStudentNameAndStudentNumber(String studentName, String studentNumber);
}
