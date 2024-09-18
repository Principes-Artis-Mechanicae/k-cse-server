package knu.univ.cse.server.domain.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import knu.univ.cse.server.domain.model.student.oauth.OAuthUserInfo;

public interface OAuth2UserInfoRepository extends JpaRepository<OAuthUserInfo, Long> {
	Optional<OAuthUserInfo> findByEmail(String email);
	boolean existsByEmail(String email);
}
