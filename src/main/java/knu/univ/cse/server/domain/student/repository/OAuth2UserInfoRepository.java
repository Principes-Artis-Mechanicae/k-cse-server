package knu.univ.cse.server.domain.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.student.entity.OAuth2UserInfo;

public interface OAuth2UserInfoRepository extends JpaRepository<OAuth2UserInfo, Long> {
	OAuth2UserInfo findByEmail(String email);
	boolean existsByEmail(String email);
}
