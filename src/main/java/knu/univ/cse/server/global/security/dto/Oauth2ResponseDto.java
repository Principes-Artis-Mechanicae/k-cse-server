package knu.univ.cse.server.global.security.dto;

import knu.univ.cse.server.domain.model.student.oauth2.OAuth2UserInfo;

public interface Oauth2ResponseDto {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    OAuth2UserInfo toEntity();
}
