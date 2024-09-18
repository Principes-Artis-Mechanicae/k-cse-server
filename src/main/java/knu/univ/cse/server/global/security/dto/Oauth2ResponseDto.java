package knu.univ.cse.server.global.security.dto;

import knu.univ.cse.server.domain.model.student.oauth.OAuthUserInfo;

public interface Oauth2ResponseDto {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    OAuthUserInfo toEntity();
}
