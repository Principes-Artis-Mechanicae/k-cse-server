package knu.univ.cse.server.domain.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findStudentById(Long id);
	Student findByStudentNameAndStudentNumber(String studentName, String studentNumber);
}
