package knu.univ.cse.server.domain.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.student.oauth2.OAuth2UserInfo;

public interface OAuth2UserInfoRepository extends JpaRepository<OAuth2UserInfo, Long> {
	OAuth2UserInfo findByEmail(String email);
	boolean existsByEmail(String email);
}
