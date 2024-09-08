package knu.univ.cse.server.domain.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.student.entity.OAuth2UserInfo;
import knu.univ.cse.server.domain.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByStudentNumber(String studentNumber);
	boolean existsByOAuth2UserInfo(OAuth2UserInfo oAuth2UserInfo);
	Student findByOAuth2UserInfo(OAuth2UserInfo oAuth2UserInfo);
}
